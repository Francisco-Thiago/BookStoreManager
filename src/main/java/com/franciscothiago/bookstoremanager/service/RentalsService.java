package com.franciscothiago.bookstoremanager.service;


import com.franciscothiago.bookstoremanager.dto.MessageDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsRequestDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsResponseDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsUpdateDTO;
import com.franciscothiago.bookstoremanager.enums.Status;
import com.franciscothiago.bookstoremanager.exception.*;
import com.franciscothiago.bookstoremanager.mapper.RentalsMapper;
import com.franciscothiago.bookstoremanager.model.Book;
import com.franciscothiago.bookstoremanager.model.Rentals;
import com.franciscothiago.bookstoremanager.model.User;
import com.franciscothiago.bookstoremanager.repository.RentalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalsService {
    private static final RentalsMapper rentalsMapper = RentalsMapper.INSTANCE;

    private final RentalsRepository rentalsRepository;

    private final BookService bookService;

    private final UserService userService;


    @Autowired
    public RentalsService(RentalsRepository rentalsRepository, BookService bookService, UserService userService) {
        this.rentalsRepository = rentalsRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    public Page<RentalsResponseDTO> findAll(Pageable pageable) {
        return rentalsRepository.findAll(pageable)
                .map(rentalsMapper::toDTO);
    }

    public RentalsResponseDTO findById(Long id) {
        return rentalsRepository.findById(id)
                .map(rentalsMapper::toDTO)
                .orElseThrow(() -> new RentalsNotFoundException(id));
    }

    public MessageDTO create(RentalsRequestDTO rentalsRequestDTO) {
        verifyIfExists(rentalsRequestDTO.getId());
        Book foundBook = bookService.verifyAndGetIfExists(rentalsRequestDTO.getBookId());
        User foundUser = userService.verifyAndGetIfExists(rentalsRequestDTO.getUserId());
        verifyIfCreateIsPossible(foundUser, foundBook);

        Rentals rentalsToCreate = rentalsMapper.toModel(rentalsRequestDTO);
        rentalsToCreate.setBook(foundBook);
        rentalsToCreate.setUser(foundUser);
        rentalsToCreate.setEntryDate(LocalDate.now());
        rentalsToCreate.setReturnDate(null);
        rentalsToCreate.setStatus(defineEnumTypeValue(rentalsToCreate.getReturnDate(), rentalsToCreate.getExpirationDate()));
        verifyAuthenticityOfDates(rentalsToCreate.getEntryDate(), rentalsToCreate.getExpirationDate());

        bookService.decrementQuantity(foundBook);

        Rentals rentalsCreated = rentalsRepository.save(rentalsToCreate);

        String createdMessage = String.format("Rental with id %s was created successfully", rentalsCreated.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public MessageDTO update(Long id, RentalsUpdateDTO rentalsUpdateDTO) {
        Rentals foundRental = verifyAndGetIfExists(id);
        User foundUser = userService.verifyAndGetIfExists(rentalsUpdateDTO.getUserId());
        Book foundBook = bookService.verifyAndGetIfExists(rentalsUpdateDTO.getBookId());

        verifyIfUpdateIsPossible(id, foundUser, foundBook);

        rentalsUpdateDTO.setId(foundRental.getId());
        Rentals rentalToCreate = rentalsMapper.toModel(rentalsUpdateDTO);
        rentalToCreate.setUser(foundUser);
        rentalToCreate.setBook(foundBook);
        rentalToCreate.setEntryDate(foundRental.getEntryDate());
        verifyAuthenticityOfDates(rentalToCreate);
        rentalToCreate.setStatus(defineEnumTypeValue(rentalToCreate.getReturnDate(), rentalToCreate.getExpirationDate()));
        checkIfUpdateIsTheSame(foundRental, rentalToCreate);
        Rentals createdRental = rentalsRepository.save(rentalToCreate);

        if(createdRental.getStatus() != Status.WAITING) {
            bookService.decrementQuantity(foundBook);
        }

        verifyIfBookStatusWasNull(id, foundBook);

        String createdMessage = String.format("Rental with id %d has been updated successfully", createdRental.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public void deleteById(Long id) {
        rentalsRepository.deleteById(id);
    }

    private void verifyIfExists(Long id) {
        Optional<Rentals> duplicatedRentals = rentalsRepository.findById(id);
        if(duplicatedRentals.isPresent()) {
            throw new RentalsAlreadyExistsException(id);
        }
    }

    private Rentals verifyAndGetIfExists(Long id) {
        return rentalsRepository.findById(id)
                .orElseThrow(() -> new RentalsAlreadyExistsException(id));
    }

    private void verifyAuthenticityOfDates(Rentals rental) {
        LocalDate entryDate = rental.getEntryDate();
        LocalDate returnDate = rental.getReturnDate();
        LocalDate expirationDate = rental.getExpirationDate();

        if(returnDate.compareTo(entryDate) < 0 || expirationDate.compareTo(entryDate) < 0) {
            throw new InvalidDateException("Date of return or expiration is invalid.");
        }
    }

    private void verifyIfBookStatusWasNull(Long id, Book book) {
        Optional<Rentals> rental = rentalsRepository.findById(id).filter(rent -> rent.getStatus() != Status.WAITING);
        if(!rental.isPresent()) {
            bookService.incrementQuantity(book);
        }
    }

    private void verifyIfCreateIsPossible(User user, Book book) {
        Optional<Rentals> foundRental = rentalsRepository.findByUserAndBook(user, book).filter(rentals -> rentals.getReturnDate() == null);

        if(foundRental.isPresent()) {
            throw new RentalUpdateIsNotPossibleException("The user has not yet returned the past book.");
        }
    }

    private boolean verifyIfUpdateIsPossible(Long id, User user, Book book) {
        Optional<Rentals> foundRental = rentalsRepository.findByUserAndBook(user, book).filter(rentals -> (rentals.getReturnDate() == null && rentals.getId() != id));

        if(foundRental.isPresent()) {
            throw new RentalUpdateIsNotPossibleException("Unable to try to recreate a record.");
        } else {
            return true;
        }
    }

    private void checkIfUpdateIsTheSame(Rentals foundRental, Rentals newRental) {
        if(foundRental.equals(newRental)) {
            throw new UpdateHasNoChangesException("Unable to update the same data.");
        }
    }

    private Status defineEnumTypeValue(LocalDate returnDate, LocalDate expirationDate) {
        if(returnDate == null) {
            return Status.WAITING;
        } else if(returnDate.compareTo(expirationDate) > 0) {
            return Status.RETURNED_AFTER;
        }else if(returnDate.compareTo(expirationDate) < 0){
            return Status.RETURNED_BEFORE;
        } else {
            return Status.RETURNED_BEFORE;
        }
    }

    private void verifyAuthenticityOfDates(LocalDate entryDate, LocalDate expirationDate) {
        if(expirationDate.compareTo(entryDate) < 0) {
            throw new InvalidDateException("Date of return or expiration is invalid.");
        }
    }

    public void deleteByUser(Long id) {
        User user = userService.verifyAndGetIfExists(id);
        List<Rentals> rentals = rentalsRepository.findByUser(user);
        rentals.stream().forEach((rentalList) -> {
            rentalsRepository.deleteById(rentalList.getId());
        });
    }

    public void deleteByBook(Long id) {
        Book book = bookService.verifyAndGetIfExists(id);
        List<Rentals> rentals = rentalsRepository.findByBook(book);
        rentals.stream().forEach((rentalList) -> {
            rentalsRepository.deleteById(rentalList.getId());
        });
    }

}

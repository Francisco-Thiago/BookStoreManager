package com.franciscothiago.bookstoremanager.service;


import com.franciscothiago.bookstoremanager.exception.InvalidDateException;
import com.franciscothiago.bookstoremanager.exception.RentalsAlreadyExistsException;
import com.franciscothiago.bookstoremanager.exception.RentalsNotFoundException;
import com.franciscothiago.bookstoremanager.model.Book;
import com.franciscothiago.bookstoremanager.model.Rentals;
import com.franciscothiago.bookstoremanager.dto.RentalsRequestDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsResponseDTO;
import com.franciscothiago.bookstoremanager.mapper.RentalsMapper;
import com.franciscothiago.bookstoremanager.repository.RentalsRepository;
import com.franciscothiago.bookstoremanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    public RentalsResponseDTO create(RentalsRequestDTO rentalsRequestDTO) {
        verifyIfExists(rentalsRequestDTO.getId());

        Book foundBook = bookService.verifyAndGetIfExists(rentalsRequestDTO.getBookId());
        User foundUser = userService.verifyAndGetIfExists(rentalsRequestDTO.getUserId());

        Rentals rentalsToCreate = rentalsMapper.toModel(rentalsRequestDTO);
        rentalsToCreate.setBook(foundBook);
        rentalsToCreate.setUser(foundUser);

        Rentals rentalsCreated = rentalsRepository.save(rentalsToCreate);

        return rentalsMapper.toDTO(rentalsCreated);
    }


    public List<RentalsResponseDTO> findAll() {
        return rentalsRepository.findAll()
                .stream()
                .map(rentalsMapper::toDTO)
                .collect(Collectors.toList());
    }
    public RentalsResponseDTO findById(Long id) {
        return rentalsRepository.findById(id)
                .map(rentalsMapper::toDTO)
                .orElseThrow(() -> new RentalsNotFoundException(id));
    }

    public void deleteById(Long id) {
        rentalsRepository.deleteById(id);
    }

//    public RentalsResponseDTO update(Long id, RentalsDTO rentalsDTO) {
//
//        Rentals foundRental = verifyAndGetIfExists(id);
//        rentalsDTO.setId(foundRental.getId());
//        rentalsDTO.setEntryDate(foundRental.getEntryDate());
//
//        verifyAuthenticityOfDates(foundRental, rentalsDTO);
//        verifyIfExists(rentalsDTO.getId());
//
//        Rentals rentalToCreate = rentalsMapper.toModel(rentalsDTO);
//        Rentals createdRental = rentalsRepository.save(rentalToCreate);
//
//        return rentalsMapper.toDTO(createdRental);
//    }

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

    private void verifyAuthenticityOfDates(Rentals oldRental, RentalsRequestDTO newRental) {
        //Unique
        LocalDate entryDate = oldRental.getEntryDate();

        // NEW
        LocalDate newReturn = newRental.getReturnDate();
        LocalDate newExpiration = newRental.getExpirationDate();

        if(newExpiration.compareTo(entryDate) < 0 && newReturn.compareTo(entryDate) < 0) {
            throw new InvalidDateException(newExpiration.toString(), newReturn.toString());
        }
    }
}

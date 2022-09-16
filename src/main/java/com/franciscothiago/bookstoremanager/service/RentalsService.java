package com.franciscothiago.bookstoremanager.service;


import com.franciscothiago.bookstoremanager.dto.MessageDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsRequestDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsResponseDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsUpdateDTO;
import com.franciscothiago.bookstoremanager.enums.Role;
import com.franciscothiago.bookstoremanager.enums.Status;
import com.franciscothiago.bookstoremanager.exception.*;
import com.franciscothiago.bookstoremanager.mapper.RentalsMapper;
import com.franciscothiago.bookstoremanager.model.Book;
import com.franciscothiago.bookstoremanager.model.Rentals;
import com.franciscothiago.bookstoremanager.model.User;
import com.franciscothiago.bookstoremanager.repository.RentalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RentalsService {

    private static final RentalsMapper rentalsMapper = RentalsMapper.INSTANCE;

    private final RentalsRepository rentalsRepository;

    private final BookService bookService;

    private final UserService userService;

    private final RentalsService rentalsService;


    @Autowired
    @Lazy
    public RentalsService(RentalsRepository rentalsRepository, BookService bookService, UserService userService, RentalsService rentalsService) {
        this.rentalsRepository = rentalsRepository;
        this.bookService = bookService;
        this.userService = userService;
        this.rentalsService = rentalsService;
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
        verifyUserRole(foundUser.getRole().getDescription());
        verifyIfCreateIsPossible(foundUser, foundBook);
        verifyBookQuantity(foundBook);

        Rentals rentalsToCreate = rentalsMapper.toModel(rentalsRequestDTO);
        rentalsToCreate.setBook(foundBook);
        rentalsToCreate.setUser(foundUser);
        rentalsToCreate.setEntryDate(LocalDate.now());
        rentalsToCreate.setReturnDate(null);
        rentalsToCreate.setStatus(defineEnumTypeValue(rentalsToCreate.getReturnDate(), rentalsToCreate.getExpirationDate()));
        verifyAuthenticityOfDates(rentalsToCreate.getEntryDate(), rentalsToCreate.getExpirationDate());

        bookService.decrementQuantity(foundBook);

        Rentals rentalsCreated = rentalsRepository.save(rentalsToCreate);

        String createdMessage = String.format("Aluguel com id %s foi criado com sucesso.", rentalsCreated.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public MessageDTO updateReturn(Long id) {
        Rentals rentalToCreate = verifyAndGetIfExists(id);
        verifyIfReturnAlreadyExists(rentalToCreate.getReturnDate());
        verifyQuantityAndSet(rentalToCreate.getReturnDate(), rentalToCreate.getBook());
        rentalToCreate.setReturnDate(LocalDate.now());
        rentalToCreate.setStatus(defineEnumTypeValue(rentalToCreate.getReturnDate(), rentalToCreate.getExpirationDate()));
        Rentals createdRental = rentalsRepository.save(rentalToCreate);

        String createdMessage = String.format("Aluguel com id %d foi retornado com sucesso.", createdRental.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public MessageDTO updateExpiration(Long id, RentalsUpdateDTO rentalsUpdateDTO) {
        Rentals rentalToCreate = verifyAndGetIfExists(id);
        checkIfUpdateDateIsTheSame(rentalToCreate.getExpirationDate(), rentalsUpdateDTO.getExpirationDate());
        rentalToCreate.setExpirationDate(rentalsUpdateDTO.getExpirationDate());
        verifyExpirationDate(rentalToCreate.getExpirationDate(), rentalToCreate.getEntryDate());
        rentalToCreate.setStatus(defineEnumTypeValue(rentalToCreate.getReturnDate(), rentalToCreate.getExpirationDate()));
        Rentals createdRental = rentalsRepository.save(rentalToCreate);

        String createdMessage = String.format("Aluguel com id %d foi atualizado com sucesso.", createdRental.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }


    private void verifyBookQuantity(Book book) {
        if(book.getQuantity() <= 0) {
            throw new RentalIsNotPossibleException("Quantidade de livros é 0!");
        }
    }

    private void verifyIfReturnAlreadyExists(LocalDate returnDate) {
        if(returnDate != null) {
            throw new RentalUpdateIsNotPossibleException("O aluguel não pode ser retornado novamente.");
        }
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

    private void verifyExpirationDate(LocalDate expirationDate, LocalDate entryDate) {
        if(expirationDate.isBefore(entryDate)) {
            throw new InvalidDateException(expirationDate.toString());
        }
    }

    private void verifyQuantityAndSet(LocalDate returnDate, Book book) {
        if(returnDate == null) {
            bookService.decrementQuantity(book);
        }
    }

    private void verifyIfCreateIsPossible(User user, Book book) {
        Optional<Rentals> foundRental = rentalsRepository.findByUserAndBookAndStatus(user, book, Status.WAITING);

        if(foundRental.isPresent()) {
            throw new RentalUpdateIsNotPossibleException("O usuário não retornou o último livro.");
        }
    }

    private void verifyUserRole(String role) {
        if(role == Role.ADMIN.getDescription()) {
            throw new RoleNotAllowedException("Administradores do sistema não podem alugar livros!");
        }
    }

    private void checkIfUpdateDateIsTheSame(LocalDate oldDate, LocalDate newDate) {
        if(oldDate.isEqual(newDate)) {
            throw new UpdateHasNoChangesException("Não há alteração nos dados passados.");
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
            throw new InvalidDateException("A data de retorno ou de expiração é inválida.");
        }
    }

    public boolean verifyRentalsOfUsers(Long id) {
        User user = userService.verifyAndGetIfExists(id);
        List<Rentals> rentals = rentalsRepository.findByUser(user);
        System.out.println(rentals);

        if(rentals.size() > 0) {
            throw new RentalUpdateIsNotPossibleException("Não é possível excluir este usuário. Exclua os aluguéis associados para poder realizar esta função.");
        } else {
            return true;
        }
    }

    public void deleteByBook(Long id) {
        Book book = bookService.verifyAndGetIfExists(id);
        List<Rentals> rentals = rentalsRepository.findByBook(book);
        rentals.stream().forEach((rentalList) -> {
            rentalsRepository.deleteById(rentalList.getId());
        });
    }

}

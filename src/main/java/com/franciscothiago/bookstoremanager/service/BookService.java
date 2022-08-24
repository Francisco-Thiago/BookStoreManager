package com.franciscothiago.bookstoremanager.service;

import com.franciscothiago.bookstoremanager.dto.BookRequestDTO;
import com.franciscothiago.bookstoremanager.dto.BookResponseDTO;
import com.franciscothiago.bookstoremanager.dto.MessageDTO;
import com.franciscothiago.bookstoremanager.exception.BookAlreadyExistsException;
import com.franciscothiago.bookstoremanager.exception.BookNotFoundException;
import com.franciscothiago.bookstoremanager.exception.InvalidDateException;
import com.franciscothiago.bookstoremanager.mapper.BookMapper;
import com.franciscothiago.bookstoremanager.model.Book;
import com.franciscothiago.bookstoremanager.model.Publisher;
import com.franciscothiago.bookstoremanager.repository.BookRepository;
import com.franciscothiago.bookstoremanager.repository.RentalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    public static final BookMapper bookMapper = BookMapper.INSTANCE;

    private BookRepository bookRepository;

    private PublisherService publisherService;

    private RentalsRepository rentalsRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PublisherService publisherService, RentalsRepository rentalsRepository) {
        this.bookRepository = bookRepository;
        this.publisherService = publisherService;
        this.rentalsRepository = rentalsRepository;
    }

    public MessageDTO create(BookRequestDTO bookRequestDTO) {
        verifyIfExists(bookRequestDTO.getId(), bookRequestDTO.getName(), bookRequestDTO.getCode());
        verifyIfDateIsValid(bookRequestDTO.getRelease());
        Publisher foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());
        Book bookToCreate = bookMapper.toModel(bookRequestDTO);
        bookToCreate.setPublisher(foundPublisher);
        Book createdBook = bookRepository.save(bookToCreate);

//        return bookMapper.toDTO(createdBook);
        String createdMessage = String.format("Book %s with id %d was created successfully",  createdBook.getName(), createdBook.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public MessageDTO update(Long id, BookRequestDTO bookRequestDTO) {
        Book foundBook = verifyAndGetIfExists(id);
        Publisher foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());

        verifyIfDateIsValid(bookRequestDTO.getRelease());
        verifyIfTheNameChanged(bookRequestDTO.getName(), foundBook.getName());

        bookRequestDTO.setId(foundBook.getId());
        bookRequestDTO.setCode(foundBook.getCode());
        bookRequestDTO.setRelease(foundBook.getRelease());

        Book bookToCreate = bookMapper.toModel(bookRequestDTO);
        bookToCreate.setPublisher(foundPublisher);
        Book createdBook = bookRepository.save(bookToCreate);

        String createdMessage = String.format("Book with id %d has been updated successfully", createdBook.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    private void verifyIfTheNameChanged(String oldName, String newName) {
        if(!oldName.equals(newName)) {
            verifyIfExists(newName);
        }
    }

    public Book verifyAndGetIfExists(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    private void verifyIfExists(Long id, String name, String code) {
        Optional<Book> foundBook = bookRepository.findByIdNameOrCode(id, name, code);
        if(foundBook.isPresent()) {
            throw new BookAlreadyExistsException(id, name, code);
        }
    }

    private void verifyIfExists(String name) {
        Optional<Book> foundBook = bookRepository.findByName(name);
        if(foundBook.isPresent()) {
            throw new BookAlreadyExistsException(name);
        }
    }

    private void verifyIfDateIsValid(LocalDate release) {
        LocalDate currentDate = LocalDate.now();
        if(release.isAfter(currentDate)) {
            throw new InvalidDateException(release.toString());
        }
    }

    public List<BookResponseDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookResponseDTO findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

}

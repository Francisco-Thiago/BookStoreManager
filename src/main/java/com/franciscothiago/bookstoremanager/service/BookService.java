package com.franciscothiago.bookstoremanager.service;

import com.franciscothiago.bookstoremanager.dto.BookRequestDTO;
import com.franciscothiago.bookstoremanager.dto.BookResponseDTO;
import com.franciscothiago.bookstoremanager.dto.MessageDTO;
import com.franciscothiago.bookstoremanager.exception.BookAlreadyExistsException;
import com.franciscothiago.bookstoremanager.exception.BookNotFoundException;
import com.franciscothiago.bookstoremanager.exception.PublisherIsNotPossibleToUpdateException;
import com.franciscothiago.bookstoremanager.exception.UpdateHasNoChangesException;
import com.franciscothiago.bookstoremanager.mapper.BookMapper;
import com.franciscothiago.bookstoremanager.model.Book;
import com.franciscothiago.bookstoremanager.model.Publisher;
import com.franciscothiago.bookstoremanager.repository.BookRepository;
import com.franciscothiago.bookstoremanager.repository.RentalsRepository;
import com.franciscothiago.bookstoremanager.utils.StringPatterns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private StringPatterns stringPatterns;

    private RentalsService rentalsService;

    @Autowired
    @Lazy
    public BookService(BookRepository bookRepository, PublisherService publisherService, RentalsRepository rentalsRepository, StringPatterns stringPatterns, RentalsService rentalsService) {
        this.bookRepository = bookRepository;
        this.publisherService = publisherService;
        this.rentalsRepository = rentalsRepository;
        this.stringPatterns = stringPatterns;
        this.rentalsService = rentalsService;
    }

    public Page<BookResponseDTO> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toDTO);
    }

    public BookResponseDTO findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public MessageDTO create(BookRequestDTO bookRequestDTO) {
        bookRequestDTO.setAuthor(stringPatterns.normalize(bookRequestDTO.getAuthor()));
        bookRequestDTO.setName(stringPatterns.normalize(bookRequestDTO.getName()));

        verifyIfExists(bookRequestDTO.getId(), bookRequestDTO.getName(), bookRequestDTO.getCode());

        Publisher foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());
        Book bookToCreate = bookMapper.toModel(bookRequestDTO);
        bookToCreate.setRelease(LocalDate.now());
        bookToCreate.setChangeDate(LocalDate.now());
        bookToCreate.setPublisher(foundPublisher);
        Book createdBook = bookRepository.save(bookToCreate);

        String createdMessage = String.format("Book %s with id %d was created successfully",  createdBook.getName(), createdBook.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public MessageDTO update(Long id, BookRequestDTO bookRequestDTO) {
        bookRequestDTO.setName(stringPatterns.normalize(bookRequestDTO.getName()));
        bookRequestDTO.setAuthor(stringPatterns.normalize(bookRequestDTO.getAuthor()));

        Book foundBook = verifyAndGetIfExists(id);
        Publisher foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());

        verifyIfTheNameChanged(foundBook.getName(), bookRequestDTO.getName());

        bookRequestDTO.setId(foundBook.getId());
        bookRequestDTO.setCode(foundBook.getCode());

        Book bookToCreate = bookMapper.toModel(bookRequestDTO);

        bookToCreate.setPublisher(foundPublisher);
        bookToCreate.setRelease(foundBook.getRelease());
        bookToCreate.setChangeDate(foundBook.getRelease());
        checkForChangesToUpdate(foundBook, bookToCreate);
        bookToCreate.setChangeDate(LocalDate.now());

        Book createdBook = bookRepository.save(bookToCreate);

        String createdMessage = String.format("Book with id %d has been updated successfully", createdBook.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public void deleteById(Long id) {

        bookRepository.deleteById(id);
        rentalsService.deleteByBook(id);

    }

    private void verifyIfExists(String name) {
        Optional<Book> foundBook = bookRepository.findByName(name);
        if(foundBook.isPresent()) {
            throw new BookAlreadyExistsException(name);
        }
    }

    public void decrementQuantity(Book book) {
        if(book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            bookRepository.save(book);
        }
    }

    public Book verifyAndGetIfExists(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public void incrementQuantity(Book book) {
            book.setQuantity(book.getQuantity() + 1);
            bookRepository.save(book);
    }

    private void checkForChangesToUpdate(Book foundBook, Book newBook) {
        if(foundBook.equals(newBook)) {
            throw new UpdateHasNoChangesException("Book has no changes");
        }
    }

    private void verifyIfTheNameChanged(String oldName, String newName) {
        if(!oldName.equals(newName)) {
            verifyIfExists(newName);
        }
    }

    private void verifyIfExists(Long id, String name, String code) {
        Optional<Book> foundBook = bookRepository.findByIdOrNameOrCode(id, name, code);
        if(foundBook.isPresent()) {
            throw new BookAlreadyExistsException(id, name, code);
        }
    }

    public boolean verifyByPublisher(Long id) {
        Publisher publisher = publisherService.verifyAndGetIfExists(id);
        List<Book> books = bookRepository.findByPublisher(publisher);
        if(books.size() > 0) {
            throw new PublisherIsNotPossibleToUpdateException("Publisher contains books registred. Delete before.");
        } else {
            return true;
        }
    }

}

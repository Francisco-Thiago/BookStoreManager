package com.franciscothiago.bookstoremanager.service;

import com.franciscothiago.bookstoremanager.exception.BookAlreadyExistsException;
import com.franciscothiago.bookstoremanager.exception.BookNotFoundException;
import com.franciscothiago.bookstoremanager.model.Book;
import com.franciscothiago.bookstoremanager.dto.BookRequestDTO;
import com.franciscothiago.bookstoremanager.dto.BookResponseDTO;
import com.franciscothiago.bookstoremanager.mapper.BookMapper;
import com.franciscothiago.bookstoremanager.repository.BookRepository;
import com.franciscothiago.bookstoremanager.model.Publisher;
import com.franciscothiago.bookstoremanager.service.PublisherService;
import com.franciscothiago.bookstoremanager.repository.RentalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public BookResponseDTO create(BookRequestDTO bookRequestDTO) {
        verifyIfExists(bookRequestDTO.getId(), bookRequestDTO.getName());
        Publisher foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());

        Book bookToCreate = bookMapper.toModel(bookRequestDTO);
        bookToCreate.setPublisher(foundPublisher);
        Book createdBook = bookRepository.save(bookToCreate);

        return bookMapper.toDTO(createdBook);
    }

    public BookResponseDTO update(Long id, BookRequestDTO bookRequestDTO) {
        Book foundBook = verifyAndGetIfExists(id);
        Publisher foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());

        bookRequestDTO.setId(foundBook.getId());
        bookRequestDTO.setRelease(foundBook.getRelease());

        Book bookToCreate = bookMapper.toModel(bookRequestDTO);
        bookToCreate.setPublisher(foundPublisher);
        Book createdBook = bookRepository.save(bookToCreate);

        return bookMapper.toDTO(createdBook);
    }

    public Book verifyAndGetIfExists(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    private void verifyIfExists(Long id, String name) {
        Optional<Book> foundBook = bookRepository.findByIdOrName(id, name);
        if(foundBook.isPresent()) {
            throw new BookAlreadyExistsException(id, name);
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

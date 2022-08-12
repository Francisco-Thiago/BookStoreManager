package com.franciscothiago.bookstoremanager.entity.book.service;

import com.franciscothiago.bookstoremanager.entity.book.Book;
import com.franciscothiago.bookstoremanager.entity.book.dto.BookDTO;
import com.franciscothiago.bookstoremanager.entity.book.mapper.BookMapper;
import com.franciscothiago.bookstoremanager.entity.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    public static final BookMapper bookMapper = BookMapper.INSTANCE;

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDTO create(BookDTO bookDTO) {

        // Regra de negócio para adicionar
        // - Um autor não pode ter o mesmo livro.

        verifyIfExists(bookDTO.getId(), bookDTO.getName());

        Book bookToCreate = bookMapper.toModel(bookDTO);
        Book createdBook = bookRepository.save(bookToCreate);
        return bookMapper.toDTO(createdBook);
    }

    public BookDTO update(Long id, BookDTO bookDTO) {

        // Regra de negócio para adicionar
        // - Um autor não pode ter o mesmo livro.

        Book foundBook = verifyAndGetIfExists(id);
        bookDTO.setId(foundBook.getId());
        Book bookToCreate = bookMapper.toModel(bookDTO);
        Book createdBook = bookRepository.save(bookToCreate);
        return bookMapper.toDTO(createdBook);
    }

    private Book verifyAndGetIfExists(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    private void verifyIfExists(Long id, String name) {
        Optional<Book> foundBook = bookRepository.findByIdOrName(id, name);
        if(foundBook.isPresent()) {
            throw new BookAlreadyExistsException(id, name);
        }
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    // MEU UPDATE
//    public BookDTO update(Long id, BookDTO bookDTO) {
//        Book foundBook = verifyAndGetIfExists(id);
//        Publisher foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherName());
//
//        Book bookToUpdate = bookMapper.toModel(bookRequestDTO);
//        bookToUpdate.setId(id);
//        bookToUpdate.setPublisher(foundPublisher);
//        bookToUpdate.setLaunchDate(foundBook.getLaunchDate());
//        Book updatedBook = bookRepository.save(bookToUpdate);
//        return bookMapper.toDTO(updatedBook);

    // UPDATE DO FRED
//    public BookResponseDTO update(Long id, BookRequestDTO bookRequestDTO) {
//        Book foundBook = verifyAndGetIfExists(id);
//        Publisher foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherName());
//
//        Book bookToUpdate = bookMapper.toModel(bookRequestDTO);
//        bookToUpdate.setId(id);
//        bookToUpdate.setPublisher(foundPublisher);
//        bookToUpdate.setLaunchDate(foundBook.getLaunchDate());
//        Book updatedBook = bookRepository.save(bookToUpdate);
//        return bookMapper.toDTO(updatedBook);
//    }
//
//    public Book verifyAndGetIfExists(Long id) {
//        return bookRepository.findById(id)
//                .orElseThrow(() -> new BookNotFoundException(id));
//    }
//
//    private void verifyIfExists(String name) {
//        Optional<Book> duplicatedBook = bookRepository
//                .findByName(name);
//        if(duplicatedBook.isPresent()) throw new BookAlreadyExistsException(name);
//    }
//    }

}

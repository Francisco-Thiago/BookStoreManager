package com.franciscothiago.bookstoremanager.entity.book.service;

import com.franciscothiago.bookstoremanager.entity.book.Book;
import com.franciscothiago.bookstoremanager.entity.book.dto.BookRequestDTO;
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

    public BookRequestDTO create(BookRequestDTO bookRequestDTO) {

        // Regra de negócio para adicionar
        // - Um autor não pode ter o mesmo livro.

        verifyIfExists(bookRequestDTO.getId(), bookRequestDTO.getName());

        Book bookToCreate = bookMapper.toModel(bookRequestDTO);
        Book createdBook = bookRepository.save(bookToCreate);
        return bookMapper.toDTO(createdBook);
    }

    public BookRequestDTO update(Long id, BookRequestDTO bookRequestDTO) {

        // Regra de negócio para adicionar
        // - Um autor não pode ter o mesmo livro.

        Book foundBook = verifyAndGetIfExists(id);
        bookRequestDTO.setId(foundBook.getId());
        Book bookToCreate = bookMapper.toModel(bookRequestDTO);
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

    public List<BookRequestDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookRequestDTO findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

}

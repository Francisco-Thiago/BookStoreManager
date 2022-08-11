package com.franciscothiago.bookstoremanager.entity.book.service;

import com.franciscothiago.bookstoremanager.entity.book.Book;
import com.franciscothiago.bookstoremanager.entity.book.dto.BookDTO;
import com.franciscothiago.bookstoremanager.entity.book.mapper.BookMapper;
import com.franciscothiago.bookstoremanager.entity.book.repository.BookRepository;
import com.franciscothiago.bookstoremanager.entity.publisher.service.PublisherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

        Book bookToCreate = bookMapper.toModel(bookDTO);
        Book createdBook = bookRepository.save(bookToCreate);
        return bookMapper.toDTO(createdBook);
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


}

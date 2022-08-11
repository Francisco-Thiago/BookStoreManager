package com.franciscothiago.bookstoremanager.entity.book.mapper;


import com.franciscothiago.bookstoremanager.entity.book.Book;
import com.franciscothiago.bookstoremanager.entity.book.dto.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toModel(BookDTO bookDTO);

    BookDTO toDTO(Book book);

}

package com.franciscothiago.bookstoremanager.mapper;


import com.franciscothiago.bookstoremanager.model.Book;
import com.franciscothiago.bookstoremanager.dto.BookRequestDTO;
import com.franciscothiago.bookstoremanager.dto.BookResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toModel(BookRequestDTO bookRequestDTO);

    Book toModel(BookResponseDTO bookResponseDTO);

    BookResponseDTO toDTO(Book book);

}

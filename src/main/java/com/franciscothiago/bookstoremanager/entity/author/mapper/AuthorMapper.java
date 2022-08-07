package com.franciscothiago.bookstoremanager.entity.author.mapper;

import com.franciscothiago.bookstoremanager.entity.author.Author;
import com.franciscothiago.bookstoremanager.entity.author.dto.AuthorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toModel(AuthorDTO authorDTO);

    AuthorDTO toDTO(Author author);
}

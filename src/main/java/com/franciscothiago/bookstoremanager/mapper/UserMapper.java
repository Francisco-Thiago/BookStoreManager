package com.franciscothiago.bookstoremanager.mapper;

import com.franciscothiago.bookstoremanager.dto.UserRequestDTO;
import com.franciscothiago.bookstoremanager.dto.UserResponseDTO;
import com.franciscothiago.bookstoremanager.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toModel(UserRequestDTO userRequestDTO);

    User toModel(UserResponseDTO userResponseDTO);

    UserResponseDTO toDTO(User user);
}

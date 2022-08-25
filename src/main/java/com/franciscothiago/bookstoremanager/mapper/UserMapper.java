package com.franciscothiago.bookstoremanager.mapper;

import com.franciscothiago.bookstoremanager.dto.UserDTO;
import com.franciscothiago.bookstoremanager.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toModel(UserDTO userDTO);

    UserDTO toDTO(User user);
}
package com.franciscothiago.bookstoremanager.entity.user.mapper;

import com.franciscothiago.bookstoremanager.entity.user.User;
import com.franciscothiago.bookstoremanager.entity.user.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toModel(UserDTO userDTO);

    UserDTO toDTO(User user);
}

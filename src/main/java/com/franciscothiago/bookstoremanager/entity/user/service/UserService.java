package com.franciscothiago.bookstoremanager.entity.user.service;

import com.franciscothiago.bookstoremanager.entity.user.User;
import com.franciscothiago.bookstoremanager.entity.user.dto.MessageDTO;
import com.franciscothiago.bookstoremanager.entity.user.dto.UserDTO;
import com.franciscothiago.bookstoremanager.entity.user.mapper.UserMapper;
import com.franciscothiago.bookstoremanager.entity.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    public final static UserMapper userMapper = UserMapper.INSTANCE;

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MessageDTO create(UserDTO userToCreateDTO) {
        verifyIfExists(userToCreateDTO.getEmail());
        User userToCreate = userMapper.toModel(userToCreateDTO);
        User createdUser = userRepository.save(userToCreate);

        String createdMessage = String.format("User %s with id %s successfully created", createdUser.getName(), createdUser.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void verifyIfExists(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);

        if(foundUser.isPresent()) {
            throw new UserAlreadyExistsException(email);
        }
    }
}

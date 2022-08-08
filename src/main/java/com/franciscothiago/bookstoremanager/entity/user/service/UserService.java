package com.franciscothiago.bookstoremanager.entity.user.service;

import com.franciscothiago.bookstoremanager.entity.user.User;
import com.franciscothiago.bookstoremanager.entity.user.dto.MessageDTO;
import com.franciscothiago.bookstoremanager.entity.user.dto.UserDTO;
import com.franciscothiago.bookstoremanager.entity.user.mapper.UserMapper;
import com.franciscothiago.bookstoremanager.entity.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    public final static UserMapper userMapper = UserMapper.INSTANCE;

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MessageDTO create(UserDTO userToCreateDTO) {
        verifyIfExists(userToCreateDTO.getEmail(), userToCreateDTO.getUsername());

//        User userToCreate = userMapper.toModel(userToCreateDTO);
        User user = new User();

        user.setId( userToCreateDTO.getId() );
        user.setName( userToCreateDTO.getName() );
        user.setEmail( userToCreateDTO.getEmail() );
        user.setUsername( userToCreateDTO.getUsername() );
        user.setPassword( userToCreateDTO.getPassword() );

        User createdUser = userRepository.save(user);

        String createdMessage = String.format("User %s with id %s successfully created", createdUser.getName(), createdUser.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    private void verifyIfExists(String email, String username) {
        Optional<User> foundUser = userRepository.findByEmailOrUsername(email, username);

        if(foundUser.isPresent()) {
            throw new UserAlreadyExistsException(email, username);
        }
    }
}

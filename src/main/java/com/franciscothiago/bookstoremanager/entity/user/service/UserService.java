package com.franciscothiago.bookstoremanager.entity.user.service;

import com.franciscothiago.bookstoremanager.entity.book.service.BookNotFoundException;
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

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MessageDTO create(UserDTO userToCreateDTO) {
        verifyIfExists(userToCreateDTO.getId(), userToCreateDTO.getEmail());
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


    public UserDTO update(Long id, UserDTO userDTO) {
        User foundBook = verifyAndGetIfExists(id);
        userDTO.setId(foundBook.getId());

        if(!verifyIfEmailIsTheSame(foundBook.getEmail(), userDTO.getEmail())) {
            verifyIfExists(userDTO.getEmail());
            foundBook.setEmail(userDTO.getEmail());
        }

        userDTO.setEmail(foundBook.getEmail());
        User userToCreate = userMapper.toModel(userDTO);
        User createdUser = userRepository.save(userToCreate);

        return userMapper.toDTO(createdUser);
    }

    private boolean verifyIfEmailIsTheSame(String previousEmail, String newEmail) {
        return previousEmail.equals(newEmail);
    }

    public User verifyAndGetIfExists(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserAlreadyExistsException(id));
    }

    private void verifyIfExists(Long id, String email) {
        Optional<User> foundUser = userRepository.findByIdOrEmail(id, email);

        if(foundUser.isPresent()) {
            throw new UserAlreadyExistsException(id, email);
        }
    }

    private void verifyIfExists(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);

        if(foundUser.isPresent()) {
            throw new UserAlreadyExistsException(email);
        }
    }

    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}

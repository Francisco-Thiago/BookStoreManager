package com.franciscothiago.bookstoremanager.service;

import com.franciscothiago.bookstoremanager.dto.MessageDTO;
import com.franciscothiago.bookstoremanager.dto.UserRequestDTO;
import com.franciscothiago.bookstoremanager.dto.UserResponseDTO;
import com.franciscothiago.bookstoremanager.exception.BookNotFoundException;
import com.franciscothiago.bookstoremanager.exception.UpdateHasNoChangesException;
import com.franciscothiago.bookstoremanager.exception.UserAlreadyExistsException;
import com.franciscothiago.bookstoremanager.mapper.UserMapper;
import com.franciscothiago.bookstoremanager.model.User;
import com.franciscothiago.bookstoremanager.repository.UserRepository;
import com.franciscothiago.bookstoremanager.utils.StringPatterns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    public final static UserMapper userMapper = UserMapper.INSTANCE;

    private final UserRepository userRepository;

    private final StringPatterns stringPatterns;

    @Autowired
    public UserService(UserRepository userRepository, StringPatterns stringPatterns) {
        this.userRepository = userRepository;
        this.stringPatterns = stringPatterns;
    }

    public MessageDTO create(UserRequestDTO userToCreateDTO) {
        userToCreateDTO.setUsername(userToCreateDTO.getUsername().toUpperCase());
        stringPatterns.onlyStringsValidator(userToCreateDTO.getUsername());

        verifyIfExists(userToCreateDTO.getId(), userToCreateDTO.getEmail(), userToCreateDTO.getUsername());
        User userToCreate = userMapper.toModel(userToCreateDTO);
        userToCreate.setRegistrationDate(LocalDate.now());
        User createdUser = userRepository.save(userToCreate);

        String createdMessage = String.format("User %s with id %s successfully created", createdUser.getUsername(), createdUser.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }


    public MessageDTO update(Long id, UserRequestDTO userRequestDTO) {
        User foundUser = verifyAndGetIfExists(id);
        userRequestDTO.setUsername(userRequestDTO.getUsername().toUpperCase());
        stringPatterns.onlyStringsValidator(userRequestDTO.getUsername());
        userRequestDTO.setId(foundUser.getId());

        if(!verifyIfEmailIsTheSame(foundUser.getEmail(), userRequestDTO.getEmail())) {
            verifyIfExistsByEmail(userRequestDTO.getEmail());
            foundUser.setEmail(userRequestDTO.getEmail());
        }

        if(!verifyIfUsernameIsTheSame(foundUser.getUsername(), userRequestDTO.getUsername())) {
            verifyIfExistsByUsername(userRequestDTO.getUsername());
            foundUser.setUsername(userRequestDTO.getUsername());
        }

        userRequestDTO.setEmail(foundUser.getEmail());
        userRequestDTO.setUsername(foundUser.getUsername());
        User userToCreate = userMapper.toModel(userRequestDTO);
        userToCreate.setRegistrationDate(foundUser.getRegistrationDate());
        checkForChangesToUpdate(foundUser, userToCreate);
        User createdUser = userRepository.save(userToCreate);

        String createdMessage = String.format("User with id %d has been updated successfully", createdUser.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    private boolean verifyIfEmailIsTheSame(String oldEmail, String newEmail) {
        return oldEmail.equals(newEmail);
    }

    private boolean verifyIfUsernameIsTheSame(String oldUsername, String newUsername) {
        return oldUsername.equals(newUsername);
    }

    public User verifyAndGetIfExists(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserAlreadyExistsException(id));
    }

    private void checkForChangesToUpdate(User foundUser, User newUser) {
        if(foundUser.equals(newUser)) {
            throw new UpdateHasNoChangesException("User has no changes");
        }
    }

    private void verifyIfExistsByEmail(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);

        if(foundUser.isPresent()) {
            throw new UserAlreadyExistsException(email);
        }
    }

    private void verifyIfExistsByUsername(String username) {
        Optional<User> foundUser = userRepository.findByUsername(username);

        if(foundUser.isPresent()) {
            throw new UserAlreadyExistsException(username);
        }
    }


    private void verifyIfExists(Long id, String email, String username) {
        Optional<User> foundUser = userRepository.findByIdOrEmailOrUsername(id, email, username);

        if(foundUser.isPresent()) {
            throw new UserAlreadyExistsException(id, email, username);
        }
    }

    public UserResponseDTO findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}

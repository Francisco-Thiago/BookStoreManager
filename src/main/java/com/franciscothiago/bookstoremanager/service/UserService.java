package com.franciscothiago.bookstoremanager.service;

import com.franciscothiago.bookstoremanager.dto.MessageDTO;
import com.franciscothiago.bookstoremanager.dto.UserDTO;
import com.franciscothiago.bookstoremanager.exception.BookNotFoundException;
import com.franciscothiago.bookstoremanager.exception.UserAlreadyExistsException;
import com.franciscothiago.bookstoremanager.mapper.UserMapper;
import com.franciscothiago.bookstoremanager.model.User;
import com.franciscothiago.bookstoremanager.repository.UserRepository;
import com.franciscothiago.bookstoremanager.utils.StringPatterns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public MessageDTO create(UserDTO userToCreateDTO) {
        userToCreateDTO.setName(userToCreateDTO.getName().toUpperCase().replaceAll("Ç", "C"));
        stringPatterns.onlyStringsValidator(userToCreateDTO.getName());

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
        userDTO.setName(userDTO.getName().toUpperCase().replaceAll("Ç", "C"));
        stringPatterns.onlyStringsValidator(userDTO.getName());
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

//    private void verifyIfUpdateIsInvalid(User oldUser, User newUser) {
//        String {ema} = oldUser;
//        String newEmail = newUser.getEmail();
//        String oldName = oldUser.getName();
//        String newName = newUser.getName();
//        String oldAddress = oldUser.getAddress();
//        String newAddress = newUser.ge
//    }

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

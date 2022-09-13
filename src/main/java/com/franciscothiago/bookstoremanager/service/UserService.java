package com.franciscothiago.bookstoremanager.service;

import com.franciscothiago.bookstoremanager.dto.AuthenticatedUser;
import com.franciscothiago.bookstoremanager.dto.MessageDTO;
import com.franciscothiago.bookstoremanager.dto.UserRequestDTO;
import com.franciscothiago.bookstoremanager.dto.UserResponseDTO;
import com.franciscothiago.bookstoremanager.enums.Role;
import com.franciscothiago.bookstoremanager.exception.*;
import com.franciscothiago.bookstoremanager.mapper.UserMapper;
import com.franciscothiago.bookstoremanager.model.User;
import com.franciscothiago.bookstoremanager.repository.UserRepository;
import com.franciscothiago.bookstoremanager.utils.StringPatterns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {

    public final static UserMapper userMapper = UserMapper.INSTANCE;

    private final UserRepository userRepository;

    private final StringPatterns stringPatterns;

    private final PasswordEncoder passwordEncoder;

    private final RentalsService rentalsService;

    @Autowired
    @Lazy
    public UserService(UserRepository userRepository, StringPatterns stringPatterns, PasswordEncoder passwordEncoder, RentalsService rentalsService) {
        this.userRepository = userRepository;
        this.stringPatterns = stringPatterns;
        this.passwordEncoder = passwordEncoder;
        this.rentalsService = rentalsService;
    }

    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDTO);
    }

    public UserResponseDTO findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public MessageDTO create(UserRequestDTO userToCreateDTO) {

        userToCreateDTO.setUsername(userToCreateDTO.getUsername().toUpperCase());
        stringPatterns.onlyStringsValidator(userToCreateDTO.getUsername());
        userToCreateDTO.setPassword(passwordEncoder.encode(userToCreateDTO.getPassword()));

        verifyIfExists(userToCreateDTO.getId(), userToCreateDTO.getEmail(), userToCreateDTO.getUsername());
        User userToCreate = userMapper.toModel(userToCreateDTO);
        userToCreate.setRegistrationDate(LocalDate.now());
        User createdUser = userRepository.save(userToCreate);

        String createdMessage = String.format("User %s with id %s successfully created", createdUser.getUsername(), createdUser.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();

    }

    public MessageDTO update(AuthenticatedUser authenticatedUser, Long id, UserRequestDTO userRequestDTO) {
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
        userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        User userToCreate = userMapper.toModel(userRequestDTO);
        userToCreate.setRegistrationDate(foundUser.getRegistrationDate());
        checkForChangesToUpdate(foundUser, userToCreate);
        User createdUser = userRepository.save(userToCreate);

        String createdMessage = String.format("User with id %d has been updated successfully", createdUser.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public void deleteById(AuthenticatedUser authenticatedUser, Long id) {
        User currentUser = verifyAndGetIfExistsByUsername(authenticatedUser.getUsername());
        Long idUserFound = verifyAndGetIfExists(id).getId();
        String role = currentUser.getRole().getDescription();
        rentalsService.verifyRentalsOfUsers(idUserFound);

        if(Role.ADMIN.getDescription() == role && id != currentUser.getId()) {
            userRepository.deleteById(id);
        } else if(currentUser.getId() == idUserFound){
            throw new UserInUseException("User id is the same!");
        } else {
            throw new RoleNotAllowedException("Role USER cannot delete users, only admins!");
        }

    }

    public User verifyAndGetIfExists(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new InvalidStringException("User with id "+id+" is invalid."));
    }

    public User verifyAndGetIfExistsByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserAlreadyExistsException(username));
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

    private void checkForChangesToUpdate(User foundUser, User newUser) {
        if(foundUser.equals(newUser)) {
            throw new UpdateHasNoChangesException("User has no changes");
        }
    }
    private boolean verifyIfUsernameIsTheSame(String oldUsername, String newUsername) {
        return oldUsername.equals(newUsername);
    }

    private boolean verifyIfEmailIsTheSame(String oldEmail, String newEmail) {
        return oldEmail.equals(newEmail);
    }

    private void verifyIfExists(Long id, String email, String username) {
        Optional<User> foundUser = userRepository.findByIdOrEmailOrUsername(id, email, username);

        if(foundUser.isPresent()) {
            throw new UserAlreadyExistsException(id, email, username);
        }
    }

}

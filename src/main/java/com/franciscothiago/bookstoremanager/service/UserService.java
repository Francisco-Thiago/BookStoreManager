package com.franciscothiago.bookstoremanager.service;

import com.franciscothiago.bookstoremanager.dto.*;
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

    public MessageDTO createUser(UserDTO userDTO) {
        userDTO.setName(userDTO.getName().toUpperCase());
        stringPatterns.onlyStringsValidator(userDTO.getName());

        verifyIfExists(userDTO.getId(), userDTO.getEmail());
        User userToCreate = userMapper.toModel(userDTO);
        userToCreate.setRegistrationDate(LocalDate.now());
        userToCreate.setUsername(null);
        userToCreate.setPassword(null);
        userToCreate.setRole(Role.USER);
        User createdUser = userRepository.save(userToCreate);

        String createdMessage = String.format("Usuário com id %d foi criado.", createdUser.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public MessageDTO createAdmin(UserAdminDTO userAdminDTO) {

        userAdminDTO.setName(userAdminDTO.getName().toUpperCase());
        userAdminDTO.setUsername(userAdminDTO.getUsername().toUpperCase());
        stringPatterns.onlyStringsValidator(userAdminDTO.getName());
        stringPatterns.onlyStringsValidator(userAdminDTO.getUsername());
        userAdminDTO.setPassword(passwordEncoder.encode(userAdminDTO.getPassword()));

        verifyIfExists(userAdminDTO.getId(), userAdminDTO.getEmail(), userAdminDTO.getUsername());
        User userToCreate = userMapper.toModel(userAdminDTO);
        userToCreate.setRegistrationDate(LocalDate.now());
        userToCreate.setRole(Role.ADMIN);
        User createdUser = userRepository.save(userToCreate);

        String createdMessage = String.format("O administrador com id %d foi criado com sucesso.", userAdminDTO.getId());

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public MessageDTO updateUser(Long id, UserDTO userDTO) {
        userDTO.setId(id);
        User foundUser = verifyAndGetIfExists(id);
        stringPatterns.onlyStringsValidator(userDTO.getName());

        if(!verifyIfEmailIsTheSame(foundUser.getEmail(), userDTO.getEmail())) {
            verifyIfExistsByEmail(userDTO.getEmail());
            foundUser.setEmail(userDTO.getEmail());
        }

        userDTO.setEmail(foundUser.getEmail());
        User userToCreate = userMapper.toModel(userDTO);
        userToCreate.setRegistrationDate(foundUser.getRegistrationDate());
        checkForChangesToUpdate(foundUser, userToCreate);
        User createdUser = userRepository.save(userToCreate);

        String createdMessage = "O usuário foi alterado com sucesso.";

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public MessageDTO updateAdmin(AuthenticatedUser authenticatedUser, Long id, UserAdminDTO userAdminDTO) {
        User foundUser = verifyAndGetIfExists(id);
        userAdminDTO.setUsername(userAdminDTO.getUsername().toUpperCase());
        stringPatterns.onlyStringsValidator(userAdminDTO.getUsername());
        userAdminDTO.setId(foundUser.getId());

        if(!verifyIfEmailIsTheSame(foundUser.getEmail(), userAdminDTO.getEmail())) {
            verifyIfExistsByEmail(userAdminDTO.getEmail());
            foundUser.setEmail(userAdminDTO.getEmail());
        }

        if(!verifyIfUsernameIsTheSame(foundUser.getUsername(), userAdminDTO.getUsername())) {
            verifyIfExistsByUsername(userAdminDTO.getUsername());
            foundUser.setUsername(userAdminDTO.getUsername());
        }

        userAdminDTO.setEmail(foundUser.getEmail());
        userAdminDTO.setUsername(foundUser.getUsername());
        userAdminDTO.setPassword(passwordEncoder.encode(userAdminDTO.getPassword()));

        User userToCreate = userMapper.toModel(userAdminDTO);
        userToCreate.setRegistrationDate(foundUser.getRegistrationDate());
        checkForChangesToUpdate(foundUser, userToCreate);
        User createdUser = userRepository.save(userToCreate);

        String createdMessage = String.format("Administrador alterado com sucesso.");

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
            throw new UserInUseException("O id do usuário é o mesmo!");
        } else {
            throw new RoleNotAllowedException("Cargo \"usuário\" não pode deletar outros usuários, apenas administradores!");
        }

    }

    public User verifyAndGetIfExists(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new InvalidStringException(String.format("Usuário com id %d é inválido.", id)));
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
            throw new UpdateHasNoChangesException("Usuário não possui mudanças.");
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

    private void verifyIfExists(Long id, String email) {
        Optional<User> foundUser = userRepository.findByIdOrEmail(id, email);

        if(foundUser.isPresent()) {
            throw new UserAlreadyExistsException(id, email);
        }
    }

}

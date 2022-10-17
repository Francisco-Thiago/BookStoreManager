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
        createDefaultUser();
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
        userRepository.save(userToCreate);

        String createdMessage = "Usuário criado com sucesso.";

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public MessageDTO updateUser(Long id, UserDTO userDTO) {
        userDTO.setId(id);
        User foundUser = verifyAndGetIfExists(id);
        verifyRole(foundUser.getRole().getDescription());
        stringPatterns.onlyStringsValidator(userDTO.getName());

        if(verifyIfEmailsTheSame(foundUser.getEmail(), userDTO.getEmail())) {
            verifyIfExistsByEmail(userDTO.getEmail());
            foundUser.setEmail(userDTO.getEmail());
        }

        userDTO.setEmail(foundUser.getEmail());
        User userToCreate = userMapper.toModel(userDTO);
        userToCreate.setRole(Role.USER);
        userToCreate.setRegistrationDate(foundUser.getRegistrationDate());
        checkForChangesToUpdate(foundUser, userToCreate);
        userRepository.save(userToCreate);

        String createdMessage = "O usuário foi alterado com sucesso.";

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public MessageDTO updateAdmin(AdminUpdateDTO adminDTO) {
        createDefaultUser();
        User foundUser = verifyAndGetIfExists(1L);
        foundUser.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        userRepository.save(foundUser);

        return MessageDTO.builder()
                .message("Anote sua nova senha em um lugar seguro.")
                .build();
    }

    public MessageDTO deleteUser(Long id){
        User user = verifyAndGetIfExists(id);
        verifyRole(user.getRole().getDescription());
        rentalsService.verifyRentalsOfUsers(id);
        userRepository.deleteById(id);

        String createdMessage = "Usuário deletado com sucesso.";

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    private void verifyRole(String role) {
        if(role.equals(Role.ADMIN.getDescription())){
            throw new RoleNotAllowedException("Não é possível deletar ou modificar administradores!");
        }
    }

    public User verifyAndGetIfExists(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new InvalidStringException(String.format("Usuário com id %d é inválido.", id)));
    }

    private void createDefaultUser() {
        System.out.println(userRepository.findByRole(Role.ADMIN).size());
        if(userRepository.findByRole(Role.ADMIN).size() == 0) {
            AdminDTO userToCrete = AdminDTO.builder()
                    .address("Desconhecido")
                    .city("Desconhecido")
                    .name("ADMIN")
                    .username("ADMIN")
                    .password(passwordEncoder.encode("admin"))
                    .email("admin@gmail.com")
                    .build();
            User userModel = userMapper.toModel(userToCrete);
            userModel.setRegistrationDate(LocalDate.now());
            userModel.setRole(Role.ADMIN);
            userRepository.save(userModel);
        }
    }

    private void verifyIfExistsByEmail(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);

        if(foundUser.isPresent()) {
            throw new UserAlreadyExistsException(email);
        }
    }

    private void checkForChangesToUpdate(User foundUser, User newUser) {
        if(foundUser.equals(newUser)) {
            throw new UpdateHasNoChangesException("Usuário não possui mudanças.");
        }
    }

    private boolean verifyIfEmailsTheSame(String oldEmail, String newEmail) {
        return !oldEmail.equals(newEmail);
    }

    private void verifyIfExists(Long id, String email) {
        Optional<User> foundUser = userRepository.findByIdOrEmail(id, email);

        if(foundUser.isPresent() || email.equals("admin@gmail.com")) {
            throw new UserAlreadyExistsException(id, email);
        }
    }

}

package com.franciscothiago.bookstoremanager.controller;

import com.franciscothiago.bookstoremanager.docs.UserControllerDocs;
import com.franciscothiago.bookstoremanager.dto.*;
import com.franciscothiago.bookstoremanager.service.AuthenticationService;
import com.franciscothiago.bookstoremanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController implements UserControllerDocs {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public Page<UserResponseDTO> getUsers(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO createUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PutMapping("/user/{id}")
    public MessageDTO updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @PutMapping("/admin")
    public MessageDTO updateAdmin(@RequestBody @Valid AdminUpdateDTO adminUpdateDTO) {
        return userService.updateAdmin(adminUpdateDTO);
    }

    @DeleteMapping("/user/{id}")
    public MessageDTO deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PostMapping(value = "/authenticate")
    public JwtResponse createAuthenticationToken(@RequestBody @Valid JwtRequest jwtRequest) {
        return authenticationService.createAuthenticationToken(jwtRequest);
    }

}

package com.franciscothiago.bookstoremanager.controller;

import com.franciscothiago.bookstoremanager.docs.UserControllerDocs;
import com.franciscothiago.bookstoremanager.dto.*;
import com.franciscothiago.bookstoremanager.service.AuthenticationService;
import com.franciscothiago.bookstoremanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO create(@RequestBody @Valid UserRequestDTO userToCreateDTO) {
        return userService.create(userToCreateDTO);
    }

    @PutMapping("{id}")
    public MessageDTO update(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @PathVariable Long id, @RequestBody @Valid UserRequestDTO userRequestDTO) {
        return userService.update(authenticatedUser, id, userRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @PathVariable Long id) {
        userService.deleteById(authenticatedUser, id);
    }

    @PostMapping(value = "/authenticate")
    public JwtResponse createAuthenticationToken(@RequestBody @Valid JwtRequest jwtRequest) {
        return authenticationService.createAuthenticationToken(jwtRequest);
    }

}

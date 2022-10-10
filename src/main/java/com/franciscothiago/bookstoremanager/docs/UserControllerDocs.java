package com.franciscothiago.bookstoremanager.docs;

import com.franciscothiago.bookstoremanager.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("User management")
public interface UserControllerDocs {

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success to get all users"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    @ApiOperation(value = "Get all users")
    Page<UserResponseDTO> getUsers(Pageable pageable);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success to get the user"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    @ApiOperation(value = "Get user by id")
    UserResponseDTO getById(Long id);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User created."),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    @ApiOperation(value = "Create a new message")
    MessageDTO createUser(UserDTO userDTO);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Admin created"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    @ApiOperation(value = "Create a new message")
    MessageDTO createAdmin(UserAdminDTO userAdminDTO);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User updated"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    @ApiOperation(value = "Update a user")
    MessageDTO updateUser(Long id, UserDTO userDTO);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Admin updated"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    @ApiOperation(value = "Update a user")
    MessageDTO updateAdmin(AuthenticatedUser authenticatedUser, Long id, UserAdminDTO userAdminDTO);

    @ApiOperation(value = "Delete a admin by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, id deleted"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    MessageDTO deleteAdmin(AuthenticatedUser authenticatedUser, Long id);

    @ApiOperation(value = "Delete a user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, id deleted"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    MessageDTO deleteUser(Long id);

    @ApiOperation(value = "Token Authentication")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Token authenticated."),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    JwtResponse createAuthenticationToken(JwtRequest jwtRequest);

}



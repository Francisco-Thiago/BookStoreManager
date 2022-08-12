package com.franciscothiago.bookstoremanager.entity.user.controller;

import com.franciscothiago.bookstoremanager.entity.book.dto.BookDTO;
import com.franciscothiago.bookstoremanager.entity.user.User;
import com.franciscothiago.bookstoremanager.entity.user.dto.MessageDTO;
import com.franciscothiago.bookstoremanager.entity.user.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api("User management")
public interface UserControllerDocs {

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, message realized"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    @ApiOperation(value = "Create a new message")
    MessageDTO create(UserDTO userToCreateDTO);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success to get all users"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    @ApiOperation(value = "Get all users")
    List<UserDTO> getUsers();


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User updated"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    @ApiOperation(value = "Update a user")
    public UserDTO update(Long id, UserDTO userDTO);
}

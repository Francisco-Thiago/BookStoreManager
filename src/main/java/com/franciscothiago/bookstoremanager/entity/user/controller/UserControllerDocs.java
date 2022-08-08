package com.franciscothiago.bookstoremanager.entity.user.controller;

import com.franciscothiago.bookstoremanager.entity.user.dto.MessageDTO;
import com.franciscothiago.bookstoremanager.entity.user.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("User management")
public interface UserControllerDocs {

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, message realized"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    @ApiOperation(value = "Create a new message")
    public MessageDTO create(UserDTO userToCreateDTO);
}

package com.franciscothiago.bookstoremanager.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @ApiOperation(value="Greets new users.")
    @ApiResponses(
            @ApiResponse(code = 200, message = "Success Method Operation")
    )
    @GetMapping
    private String hello() {
        return "Welcome to Book Store Manager";
    }
}

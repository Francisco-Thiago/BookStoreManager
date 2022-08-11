package com.franciscothiago.bookstoremanager.entity.book.controller;

import com.franciscothiago.bookstoremanager.entity.book.dto.BookDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api("Book management")
public interface BookControllerDocs {

    @ApiOperation(value = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success Book creation"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    BookDTO create(BookDTO bookDTO);

    @ApiOperation(value = "Find a result by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success search"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    BookDTO findById(Long id);

    @ApiOperation(value = "Get all books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success Book creation"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    List<BookDTO> getBooks();

    @ApiOperation(value = "Delete a book by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, id deleted"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    void delete(Long id);

}

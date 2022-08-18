package com.franciscothiago.bookstoremanager.entity.book.controller;

import com.franciscothiago.bookstoremanager.entity.book.dto.BookRequestDTO;
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
    BookRequestDTO create(BookRequestDTO bookRequestDTO);

    @ApiOperation(value = "Find a result by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success search"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    BookRequestDTO findById(Long id);

    @ApiOperation(value = "Get all books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success Book creation"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    List<BookRequestDTO> getBooks();

    @ApiOperation(value = "Delete a book by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, id deleted"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    void delete(Long id);

    @ApiOperation(value = "Update a book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book updated!"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    public BookRequestDTO update(Long id, BookRequestDTO bookRequestDTO);

}

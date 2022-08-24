package com.franciscothiago.bookstoremanager.docs;

import com.franciscothiago.bookstoremanager.dto.BookRequestDTO;
import com.franciscothiago.bookstoremanager.dto.BookResponseDTO;
import com.franciscothiago.bookstoremanager.dto.MessageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.aspectj.bridge.Message;

import java.util.List;

@Api("Book management")
public interface BookControllerDocs {

    @ApiOperation(value = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success Book creation"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    MessageDTO create(BookRequestDTO bookRequestDTO);

    @ApiOperation(value = "Find a result by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success search"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    BookResponseDTO findById(Long id);

    @ApiOperation(value = "Get all books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success Book creation"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    List<BookResponseDTO> getBooks();

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

    public MessageDTO update(Long id, BookRequestDTO bookRequestDTO);

}

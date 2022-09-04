package com.franciscothiago.bookstoremanager.docs;

import com.franciscothiago.bookstoremanager.dto.MessageDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsRequestDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsResponseDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsUpdateDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api("Rentals management")
public interface RentalsControllerDocs {

    @ApiOperation(value = "Get all rentals")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success Rentals creation"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    List<RentalsResponseDTO> getRentals();

    @ApiOperation(value = "Find a result by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success search"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    RentalsResponseDTO findById(Long id);

    @ApiOperation(value = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success Rentals creation"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    MessageDTO create(RentalsRequestDTO rentalsRequestDTO);

    @ApiOperation(value = "Update a rental")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rental updated!"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    MessageDTO update(Long id, RentalsUpdateDTO rentalsUpdateDTO);

    @ApiOperation(value = "Delete a rental by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, id deleted"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    void delete(Long id);

}

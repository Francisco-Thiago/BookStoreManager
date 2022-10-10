package com.franciscothiago.bookstoremanager.docs;

import com.franciscothiago.bookstoremanager.dto.MessageDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsRequestDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsResponseDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsUpdateDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api("Rentals management")
public interface RentalsControllerDocs {

    @ApiOperation(value = "Get all rentals")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success Rentals creation"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    Page<RentalsResponseDTO> getRentals(Pageable pageable);

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

    @ApiOperation(value = "Update a rental by expiration.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rental updated!"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    MessageDTO updateOnlyExpiration(Long id, RentalsUpdateDTO rentalsUpdateDTO);

    @ApiOperation(value = "Update a rental by return.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rental updated!"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    MessageDTO updateOnlyReturn(Long id);

    @ApiOperation(value = "Delete a rental by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, id deleted"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    MessageDTO delete(Long id);

}

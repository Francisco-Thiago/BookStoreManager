package com.franciscothiago.bookstoremanager.entity.publisher.controller;

import com.franciscothiago.bookstoremanager.entity.publisher.dto.PublisherDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api("Publishers management")
public interface PublisherControllerDocs {

    @ApiOperation(value = "Create a new publisher")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success Publisher creation"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    PublisherDTO create(PublisherDTO publisherDTO);

    @ApiOperation(value = "Find a result by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success search"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    PublisherDTO findById(Long id);

    @ApiOperation(value = "Get all publishers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success Publisher creation"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    List<PublisherDTO> getPublishers();

    @ApiOperation(value = "Delete a publisher by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, id deleted"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    void delete(Long id);
}

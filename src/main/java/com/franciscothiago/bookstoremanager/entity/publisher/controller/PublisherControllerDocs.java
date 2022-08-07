package com.franciscothiago.bookstoremanager.entity.publisher.controller;

import com.franciscothiago.bookstoremanager.entity.publisher.dto.PublisherDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Publishers management")
public interface PublisherControllerDocs {

    @ApiOperation(value = "Create a new publisher")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success Publisher creation"),
            @ApiResponse(code = 400, message = "Missing data. Check and try again.")
    })
    public PublisherDTO create(PublisherDTO publisherDTO);

}

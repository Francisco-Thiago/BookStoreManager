package com.franciscothiago.bookstoremanager.controller;

import com.franciscothiago.bookstoremanager.docs.PublisherControllerDocs;
import com.franciscothiago.bookstoremanager.dto.MessageDTO;
import com.franciscothiago.bookstoremanager.dto.PublisherRequestDTO;
import com.franciscothiago.bookstoremanager.dto.PublisherResponseDTO;
import com.franciscothiago.bookstoremanager.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
public class PublisherController implements PublisherControllerDocs {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public Page<PublisherResponseDTO> getPublishers(Pageable pageable) {
        return publisherService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public PublisherResponseDTO findById(@PathVariable Long id) {
        return publisherService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO create(@RequestBody @Valid PublisherRequestDTO publisherRequestDTO) {
        return publisherService.create(publisherRequestDTO);
    }

    @PutMapping("{id}")
    public MessageDTO update(@PathVariable Long id, @RequestBody @Valid PublisherRequestDTO publisherRequestDTO) {
        return publisherService.update(id, publisherRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        publisherService.deleteById(id);
    }

}

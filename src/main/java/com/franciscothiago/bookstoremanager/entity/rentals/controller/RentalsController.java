package com.franciscothiago.bookstoremanager.entity.rentals.controller;

import com.franciscothiago.bookstoremanager.entity.rentals.dto.RentalsDTO;
import com.franciscothiago.bookstoremanager.entity.rentals.service.RentalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/rentals")
public class RentalsController implements RentalsControllerDocs{

    private final RentalsService rentalsService;

    @Autowired
    public RentalsController(RentalsService rentalsService) {
        this.rentalsService = rentalsService;
    }

    @GetMapping
    public List<RentalsDTO> getRentals() {
        return rentalsService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RentalsDTO create(@RequestBody @Valid RentalsDTO rentalsDTO) {
        return rentalsService.create(rentalsDTO);
    }

    @PutMapping("{id}")
    public RentalsDTO update(@PathVariable Long id, @RequestBody @Valid RentalsDTO rentalsDTO) {
        return rentalsService.update(id, rentalsDTO);
    }

    @GetMapping("/{id}")
    public RentalsDTO findById(@PathVariable Long id) {
        return rentalsService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        rentalsService.deleteById(id);
    }
}

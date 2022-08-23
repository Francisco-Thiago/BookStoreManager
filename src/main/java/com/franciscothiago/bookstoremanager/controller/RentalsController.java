package com.franciscothiago.bookstoremanager.controller;

import com.franciscothiago.bookstoremanager.docs.RentalsControllerDocs;
import com.franciscothiago.bookstoremanager.dto.RentalsRequestDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsResponseDTO;
import com.franciscothiago.bookstoremanager.service.RentalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/rentals")
public class RentalsController implements RentalsControllerDocs {

    private final RentalsService rentalsService;

    @Autowired
    public RentalsController(RentalsService rentalsService) {
        this.rentalsService = rentalsService;
    }

    @GetMapping
    public List<RentalsResponseDTO> getRentals() {
        return rentalsService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RentalsResponseDTO create(@RequestBody @Valid RentalsRequestDTO rentalsRequestDTO) {
       return rentalsService.create(rentalsRequestDTO);
    }

//    @PutMapping("{id}")
//    public RentalsResponseDTO update(@PathVariable Long id, @RequestBody @Valid RentalsDTO rentalsDTO) {
//        return rentalsService.update(id, rentalsDTO);
//    }

    @GetMapping("/{id}")
    public RentalsResponseDTO findById(@PathVariable Long id) {
        return rentalsService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        rentalsService.deleteById(id);
    }
}

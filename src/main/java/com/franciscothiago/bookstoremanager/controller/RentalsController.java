package com.franciscothiago.bookstoremanager.controller;

import com.franciscothiago.bookstoremanager.docs.RentalsControllerDocs;
import com.franciscothiago.bookstoremanager.dto.MessageDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsRequestDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsResponseDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsUpdateDTO;
import com.franciscothiago.bookstoremanager.service.RentalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/rentals")
@CrossOrigin(origins = "*")
public class RentalsController implements RentalsControllerDocs {

    private final RentalsService rentalsService;

    @Autowired
    public RentalsController(RentalsService rentalsService) {
        this.rentalsService = rentalsService;
    }

    @GetMapping
    public Page<RentalsResponseDTO> getRentals(Pageable pageable) {
        return rentalsService.findAll(pageable);
    }


    @GetMapping("/{id}")
    public RentalsResponseDTO findById(@PathVariable Long id) {
        return rentalsService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO create(@RequestBody @Valid RentalsRequestDTO rentalsRequestDTO) {
       return rentalsService.create(rentalsRequestDTO);
    }

//    @PutMapping("{id}")
//    public MessageDTO update(@PathVariable Long id, @RequestBody @Valid RentalsUpdateDTO rentalsUpdateDTO) {
//        return rentalsService.update(id, rentalsUpdateDTO);
//    }

    @PutMapping("/expiration/{id}")
    public MessageDTO updateOnlyExpiration(@PathVariable Long id, @RequestBody @Valid RentalsUpdateDTO rentalsUpdateDTO) {
        return rentalsService.updateExpiration(id, rentalsUpdateDTO);
    }

    @PutMapping("/return/{id}")
    public MessageDTO updateOnlyReturn(@PathVariable Long id) {
        return rentalsService.updateReturn(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        rentalsService.deleteById(id);
    }

}

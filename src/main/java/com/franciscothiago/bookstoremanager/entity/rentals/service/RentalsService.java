package com.franciscothiago.bookstoremanager.entity.rentals.service;

import com.franciscothiago.bookstoremanager.entity.rentals.Rentals;
import com.franciscothiago.bookstoremanager.entity.rentals.dto.RentalsDTO;
import com.franciscothiago.bookstoremanager.entity.rentals.mapper.RentalsMapper;
import com.franciscothiago.bookstoremanager.entity.rentals.repository.RentalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalsService {
    private static final RentalsMapper rentalsMapper = RentalsMapper.INSTANCE;

    private final RentalsRepository rentalsRepository;

    @Autowired
    public RentalsService(RentalsRepository rentalsRepository) {
        this.rentalsRepository = rentalsRepository;
    }

    public RentalsDTO create(RentalsDTO rentalsDTO) {
        verifyIfExists(rentalsDTO.getId());

        Rentals rentalsToCreate = rentalsMapper.toModel(rentalsDTO);
        Rentals createdRentals = rentalsRepository.save(rentalsToCreate);
        return rentalsMapper.toDTO(createdRentals);
    }

    public List<RentalsDTO> findAll() {
        return rentalsRepository.findAll()
                .stream()
                .map(rentalsMapper::toDTO)
                .collect(Collectors.toList());
    }
    public RentalsDTO findById(Long id) {
        return rentalsRepository.findById(id)
                .map(rentalsMapper::toDTO)
                .orElseThrow(() -> new RentalsNotFoundException(id));
    }

    public void deleteById(Long id) {
        rentalsRepository.deleteById(id);
    }

    private void verifyIfExists(Long id) {
        Optional<Rentals> duplicatedRentals = rentalsRepository.findById(id);
        if(duplicatedRentals.isPresent()) {
            throw new RentalsAlreadyExists(id);
        }
    }
}

package com.franciscothiago.bookstoremanager.mapper;

import com.franciscothiago.bookstoremanager.model.Rentals;
import com.franciscothiago.bookstoremanager.dto.RentalsRequestDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RentalsMapper {
    RentalsMapper INSTANCE = Mappers.getMapper(RentalsMapper.class);

    Rentals toModel(RentalsRequestDTO rentalsRequestDTO);

    Rentals toModel(RentalsResponseDTO rentalsResponseDTO);

    RentalsResponseDTO toDTO(Rentals rentals);
}

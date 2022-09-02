package com.franciscothiago.bookstoremanager.entity.rentals.mapper;

import com.franciscothiago.bookstoremanager.entity.rentals.Rentals;
import com.franciscothiago.bookstoremanager.entity.rentals.dto.RentalsRequestDTO;
import com.franciscothiago.bookstoremanager.entity.rentals.dto.RentalsResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RentalsMapper {
    RentalsMapper INSTANCE = Mappers.getMapper(RentalsMapper.class);

    Rentals toModel(RentalsRequestDTO rentalsRequestDTO);

    Rentals toModel(RentalsResponseDTO rentalsResponseDTO);

    RentalsResponseDTO toDTO(Rentals rentals);
}

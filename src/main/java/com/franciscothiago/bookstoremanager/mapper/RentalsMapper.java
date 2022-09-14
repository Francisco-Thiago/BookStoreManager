package com.franciscothiago.bookstoremanager.mapper;

import com.franciscothiago.bookstoremanager.dto.RentalsRequestDTO;
import com.franciscothiago.bookstoremanager.dto.RentalsResponseDTO;
import com.franciscothiago.bookstoremanager.model.Rentals;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RentalsMapper {

    RentalsMapper INSTANCE = Mappers.getMapper(RentalsMapper.class);

    Rentals toModel(RentalsRequestDTO rentalsRequestDTO);

    RentalsResponseDTO toDTO(Rentals rentals);

}

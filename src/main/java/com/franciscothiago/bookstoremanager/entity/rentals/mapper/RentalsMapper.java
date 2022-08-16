package com.franciscothiago.bookstoremanager.entity.rentals.mapper;

import com.franciscothiago.bookstoremanager.entity.rentals.Rentals;
import com.franciscothiago.bookstoremanager.entity.rentals.dto.RentalsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RentalsMapper {
    RentalsMapper INSTANCE = Mappers.getMapper(RentalsMapper.class);

    Rentals toModel(RentalsDTO rentalsDTO);

    RentalsDTO toDTO(Rentals rentals);
}

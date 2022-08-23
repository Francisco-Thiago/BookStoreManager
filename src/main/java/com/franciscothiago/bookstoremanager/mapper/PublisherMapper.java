package com.franciscothiago.bookstoremanager.mapper;

import com.franciscothiago.bookstoremanager.model.Publisher;
import com.franciscothiago.bookstoremanager.dto.PublisherRequestDTO;
import com.franciscothiago.bookstoremanager.dto.PublisherResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublisherMapper {

    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    Publisher toModel(PublisherRequestDTO publisherRequestDTO);

    Publisher toModel(PublisherResponseDTO publisherResponseDTO);

    PublisherResponseDTO toDTO(Publisher publisher);
}

package com.franciscothiago.bookstoremanager.entity.publisher.mapper;

import com.franciscothiago.bookstoremanager.entity.publisher.Publisher;
import com.franciscothiago.bookstoremanager.entity.publisher.dto.PublisherRequestDTO;
import com.franciscothiago.bookstoremanager.entity.publisher.dto.PublisherResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublisherMapper {

    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    Publisher toModel(PublisherRequestDTO publisherRequestDTO);

    Publisher toModel(PublisherResponseDTO publisherResponseDTO);

    PublisherResponseDTO toDTO(Publisher publisher);
}

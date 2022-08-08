package com.franciscothiago.bookstoremanager.entity.publisher.mapper;

import com.franciscothiago.bookstoremanager.entity.publisher.Publisher;
import com.franciscothiago.bookstoremanager.entity.publisher.dto.PublisherDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublisherMapper {

    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    Publisher toModel(PublisherDTO publisherDTO);

    PublisherDTO toDTO(Publisher publisher);
}

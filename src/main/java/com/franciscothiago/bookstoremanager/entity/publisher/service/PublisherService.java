package com.franciscothiago.bookstoremanager.entity.publisher.service;

import com.franciscothiago.bookstoremanager.entity.publisher.Publisher;
import com.franciscothiago.bookstoremanager.entity.publisher.dto.PublisherRequestDTO;
import com.franciscothiago.bookstoremanager.entity.publisher.dto.PublisherResponseDTO;
import com.franciscothiago.bookstoremanager.entity.publisher.mapper.PublisherMapper;
import com.franciscothiago.bookstoremanager.entity.publisher.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherService {
    private static final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public PublisherResponseDTO create(PublisherRequestDTO publisherRequestDTO) {
        verifyIfExists(publisherRequestDTO.getName(), publisherRequestDTO.getCode());

        Publisher publisherToCreate = publisherMapper.toModel(publisherRequestDTO);
        Publisher createdPublisher = publisherRepository.save(publisherToCreate);
        return publisherMapper.toDTO(createdPublisher);
    }

    public List<PublisherResponseDTO> findAll() {
        return publisherRepository.findAll()
                .stream()
                .map(publisherMapper::toDTO)
                .collect(Collectors.toList());
    }
    public PublisherResponseDTO findById(Long id) {
        return publisherRepository.findById(id)
                .map(publisherMapper::toDTO)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    public void deleteById(Long id) {
        publisherRepository.deleteById(id);
    }

    private void verifyIfExists(String name, String code) {
        Optional<Publisher> duplicatedPublisher = publisherRepository.findByNameOrCode(name, code);
        if(duplicatedPublisher.isPresent()) {
            throw new PublisherAlreadyExists(name, code);
        }
    }

    public Publisher verifyAndGetIfExists(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }
}

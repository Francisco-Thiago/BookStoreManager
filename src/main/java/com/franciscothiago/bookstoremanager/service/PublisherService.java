package com.franciscothiago.bookstoremanager.service;

import com.franciscothiago.bookstoremanager.dto.MessageDTO;
import com.franciscothiago.bookstoremanager.dto.PublisherRequestDTO;
import com.franciscothiago.bookstoremanager.dto.PublisherResponseDTO;
import com.franciscothiago.bookstoremanager.exception.PublisherAlreadyExists;
import com.franciscothiago.bookstoremanager.exception.PublisherNotFoundException;
import com.franciscothiago.bookstoremanager.exception.UpdateHasNoChangesException;
import com.franciscothiago.bookstoremanager.mapper.PublisherMapper;
import com.franciscothiago.bookstoremanager.model.Publisher;
import com.franciscothiago.bookstoremanager.repository.PublisherRepository;
import com.franciscothiago.bookstoremanager.utils.StringPatterns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PublisherService {
    private static final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private final PublisherRepository publisherRepository;

    private final StringPatterns stringPatterns;

    private final BookService bookService;

    @Autowired
    @Lazy
    public PublisherService(PublisherRepository publisherRepository, StringPatterns stringPatterns, BookService bookService) {
        this.publisherRepository = publisherRepository;
        this.stringPatterns = stringPatterns;
        this.bookService = bookService;
    }

    public Page<PublisherResponseDTO> findAll(Pageable pageable) {
        return publisherRepository.findAll(pageable)
                .map(publisherMapper::toDTO);
    }

    public PublisherResponseDTO findById(Long id) {
        return publisherRepository.findById(id)
                .map(publisherMapper::toDTO)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    public MessageDTO create(PublisherRequestDTO publisherRequestDTO) {
        verifyIfExists(publisherRequestDTO.getName(), publisherRequestDTO.getCode());
        Publisher publisherToCreate = publisherMapper.toModel(publisherRequestDTO);
        publisherToCreate.setRegistrationDate(LocalDate.now());
        publisherRepository.save(publisherToCreate);

        String createdMessage = "Editora criada com sucesso!";

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public MessageDTO update(Long id, PublisherRequestDTO publisherRequestDTO) {
        Publisher foundPublisher = verifyAndGetIfExists(id);
        publisherRequestDTO.setId(foundPublisher.getId());
        Publisher publisherToCreate = publisherMapper.toModel(publisherRequestDTO);
        publisherToCreate.setRegistrationDate(foundPublisher.getRegistrationDate());
        checkForChangesToUpdate(foundPublisher, publisherToCreate);
        Publisher createdPublisher = publisherRepository.save(publisherToCreate);

        String createdMessage = "Editora atualizada com sucesso!";

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public MessageDTO deleteById(Long id) {
        bookService.verifyByPublisher(id);
        publisherRepository.deleteById(id);

        String createdMessage = "Editora deletada com sucesso!";

        return MessageDTO.builder()
                .message(createdMessage)
                .build();
    }

    public Publisher verifyAndGetIfExists(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    private void verifyIfExists(String name, String code) {
        Optional<Publisher> duplicatedPublisher = publisherRepository.findByNameOrCode(name, code);
        if(duplicatedPublisher.isPresent()) {
            throw new PublisherAlreadyExists(name, code);
        }
    }

    private void checkForChangesToUpdate(Publisher foundPublisher, Publisher publisherToCreate) {
        if(foundPublisher.equals(publisherToCreate)) {
            throw new UpdateHasNoChangesException("Editora não possui mudanças.");
        }
    }

}

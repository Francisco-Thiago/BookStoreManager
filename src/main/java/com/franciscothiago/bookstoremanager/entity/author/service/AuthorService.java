package com.franciscothiago.bookstoremanager.entity.author.service;

import com.franciscothiago.bookstoremanager.entity.author.mapper.AuthorMapper;
import com.franciscothiago.bookstoremanager.entity.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private static final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

}

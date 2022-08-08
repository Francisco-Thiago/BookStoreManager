package com.franciscothiago.bookstoremanager.entity.user.service;

import com.franciscothiago.bookstoremanager.entity.user.mapper.UserMapper;
import com.franciscothiago.bookstoremanager.entity.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public final static UserMapper userMapper = UserMapper.INSTANCE;

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

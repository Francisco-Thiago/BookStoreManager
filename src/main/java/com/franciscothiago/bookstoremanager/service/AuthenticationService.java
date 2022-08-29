package com.franciscothiago.bookstoremanager.service;

import com.franciscothiago.bookstoremanager.dto.AuthenticatedUser;
import com.franciscothiago.bookstoremanager.model.User;
import com.franciscothiago.bookstoremanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not exists", username)));
        return new AuthenticatedUser(
            user.getUsername(),
            user.getPassword(),
            user.getRole().getDescription()
        );
    }
}

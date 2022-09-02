package com.franciscothiago.bookstoremanager.service;

import com.franciscothiago.bookstoremanager.dto.AuthenticatedUser;
import com.franciscothiago.bookstoremanager.dto.JwtRequest;
import com.franciscothiago.bookstoremanager.dto.JwtResponse;
import com.franciscothiago.bookstoremanager.model.User;
import com.franciscothiago.bookstoremanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    private JwtTokenManager jwtTokenManager;

    @Autowired
    @Lazy
    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenManager jwtTokenManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenManager = jwtTokenManager;
    }

    public JwtResponse createAuthenticationToken(JwtRequest jwtRequest) {
        String username = jwtRequest.getUsername();
        authenticate(username, jwtRequest.getPassword());

        UserDetails userDetails = this.loadUserByUsername(username);
        String token = jwtTokenManager.generateToken(userDetails);

        return JwtResponse.builder()
                .jwtToken(token)
                .build();
    }

    private Authentication authenticate(String username, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with username %s", username)));
        return new AuthenticatedUser(
                user.getUsername(),
                user.getPassword(),
                user.getRole().getDescription()
        );
    }
}

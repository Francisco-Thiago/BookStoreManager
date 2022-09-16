package com.franciscothiago.bookstoremanager.config;

import com.franciscothiago.bookstoremanager.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {


    private static final String ALL_USERS_API_URL = "/api/v1/users";
    private static final String USERS_API_URL = "/api/v1/users/user/**";
    private static final String USERS_ADMIN_API_URL = "/api/v1/users/admin/**";
    private static final String PUBLISHERS_API_URL = "/api/v1/publishers/**";
    private static final String RENTALS_API_URL = "/api/v1/rentals/**";
    private static final String BOOKS_API_URL = "/api/v1/books/**";
    private static final String SWAGGER_URL = "/swagger-ui.html";
    private static final String ROLE_ADMIN = Role.ADMIN.getDescription();
    private static final String ROLE_USER = Role.USER.getDescription();
    private static final String[] SWAGGER_RESOURCES = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                             UserDetailsService userDetailsService,
                             PasswordEncoder passwordEncoder,
                             JwtRequestFilter jwtRequestFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and()
                .csrf().disable()
                .authorizeHttpRequests().antMatchers(SWAGGER_URL).permitAll()
                .antMatchers(HttpMethod.GET, ALL_USERS_API_URL).permitAll()
                .antMatchers(BOOKS_API_URL, PUBLISHERS_API_URL, USERS_API_URL, USERS_API_URL, RENTALS_API_URL).permitAll()
                .antMatchers(USERS_ADMIN_API_URL).hasAnyRole(ROLE_ADMIN)
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.headers().frameOptions().disable();

        return httpSecurity.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
        return (web) -> web.ignoring().antMatchers(SWAGGER_RESOURCES);
    }

}
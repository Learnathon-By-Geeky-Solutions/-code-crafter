package com.xenon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.common.util.JwtUtil;
import com.xenon.data.repository.UserRepository;
import com.xenon.presenter.config.ApplicationConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Test configuration for unit tests
 * This class provides necessary beans for unit testing without loading the full application context
 */
@TestConfiguration
@Import(ApplicationConfig.class)
public class TestConfig {

    /**
     * Creates a password encoder for tests
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
    
    /**
     * Creates an ObjectMapper for tests
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Configure the mapper as needed for tests
        return mapper;
    }
    
    /**
     * Creates a mock JwtUtil for tests that can be customized in tests
     */
    @Bean
    public JwtUtil jwtUtil(ObjectMapper objectMapper, UserRepository userRepository) {
        return new JwtUtil(objectMapper, userRepository);
    }
}
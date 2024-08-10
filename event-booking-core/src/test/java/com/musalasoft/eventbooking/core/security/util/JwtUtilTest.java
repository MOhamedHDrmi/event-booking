package com.musalasoft.eventbooking.core.security.util;

import com.musalasoft.eventbooking.core.security.CustomUserDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

class JwtUtilTest {

    @InjectMocks
    JwtUtil jwtUtil;

    private AutoCloseable autoCloseable;
    private String validToken;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        this.userDetails = CustomUserDetails.builder()
                                                   .name("admin")
                                                   .password("password")
                                                   .email("email")
                                                   .build();

        this.validToken = this.jwtUtil.generateToken(userDetails);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void extractUsername() {
        Assertions.assertEquals("admin", this.jwtUtil.extractUsername(this.validToken));
    }

    @Test
    void generateToken() {
        Assertions.assertNotNull(jwtUtil.generateToken(this.userDetails));
    }

    @Test
    void validateToken() {
        Assertions.assertTrue(jwtUtil.validateToken(this.validToken, this.userDetails));
    }
}
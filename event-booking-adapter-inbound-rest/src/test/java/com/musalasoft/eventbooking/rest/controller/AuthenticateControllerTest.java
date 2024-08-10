package com.musalasoft.eventbooking.rest.controller;

import com.musalasoft.eventbooking.core.api.command.AuthenticateCommand;
import com.musalasoft.eventbooking.core.exception.InvalidParameterValueException;
import com.musalasoft.eventbooking.core.portin.AuthenticateUseCase;
import com.musalasoft.eventbooking.rest.mapper.CredentialsMapper;
import com.musalasoft.eventbooking.rest.model.Credentials;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.when;

class AuthenticateControllerTest {

    @Mock
    private AuthenticateUseCase authenticateUseCase;

    @Mock
    private CredentialsMapper mapper;

    @InjectMocks
    private AuthenticateController authenticateController;

    private  AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void authenticate_WithValidInput() {
        // given
        Credentials credentials = new Credentials();
        credentials.setPassword("password");
        credentials.setEmail("email@email.com");

        AuthenticateCommand command = new AuthenticateCommand("email@email.com", "password");
        String token = "someToken";
        String expectedHeader = "Bearer " + token;

        when(this.mapper.toAuthenticateCommand(credentials)).thenReturn(command);
        when(this.authenticateUseCase.authenticate(command)).thenReturn(token);

        // when
        ResponseEntity<Void> response = this.authenticateController.authenticate(credentials);

        // assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(List.of(expectedHeader), response.getHeaders().getOrEmpty("Authorization"));
    }

    @Test
    void authenticate_WithInvalidInput() {
        // given
        Credentials credentials = new Credentials();
        credentials.setPassword("password");
        credentials.setEmail("email2email.com");

        AuthenticateCommand command = new AuthenticateCommand("email2email.com", "password");
        String token = "someToken";
        String expectedHeader = "Bearer " + token;

        when(this.mapper.toAuthenticateCommand(credentials)).thenReturn(command);
        when(this.authenticateUseCase.authenticate(command)).thenReturn(token);

        // when
        Assertions.assertThrows(InvalidParameterValueException.class,
                                () -> this.authenticateController.authenticate(credentials),
                                "Invalid Email");

    }
}
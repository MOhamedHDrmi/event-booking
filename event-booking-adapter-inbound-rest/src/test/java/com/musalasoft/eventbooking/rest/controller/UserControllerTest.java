package com.musalasoft.eventbooking.rest.controller;

import com.musalasoft.eventbooking.core.exception.InvalidParameterValueException;
import com.musalasoft.eventbooking.core.portin.UserOperationsUseCase;
import com.musalasoft.eventbooking.rest.mapper.UserMapper;
import com.musalasoft.eventbooking.rest.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    UserOperationsUseCase userOperationsUseCase;
    @Mock
    UserMapper mapper;
    @InjectMocks
    UserController userController;

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
    void createUser_WithValidInput() {
        User user = new User();
        user.setName("name");
        user.setEmail("email@email.com.eg");
        user.setPassword("password");

        // when
        when(this.mapper.toCreateUserCommand(any())).thenReturn(null);
        doNothing().when(this.userOperationsUseCase).create(any());

        // act
        ResponseEntity<Void> response = this.userController.createUser(user);

        // assert
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createUser_WithInvalidInput() {
        User user = new User();
        user.setName("name");
        user.setEmail("email2email.com.eg");
        user.setPassword("password");

        // when
        when(this.mapper.toCreateUserCommand(any())).thenReturn(null);
        doNothing().when(this.userOperationsUseCase).create(any());

        // assert
        Assertions.assertThrows(InvalidParameterValueException.class,
                                () -> this.userController.createUser(user),
                                "Invalid Email");
    }
}
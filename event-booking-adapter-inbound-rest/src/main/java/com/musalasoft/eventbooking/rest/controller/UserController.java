package com.musalasoft.eventbooking.rest.controller;

import com.musalasoft.eventbooking.core.api.command.CreateUserCommand;
import com.musalasoft.eventbooking.core.exception.InvalidParameterValueException;
import com.musalasoft.eventbooking.core.portin.UserOperationsUseCase;
import com.musalasoft.eventbooking.rest.api.UsersApi;
import com.musalasoft.eventbooking.rest.mapper.UserMapper;
import com.musalasoft.eventbooking.rest.model.User;
import com.musalasoft.eventbooking.rest.util.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UsersApi {

    private final UserOperationsUseCase userOperationsUseCase;
    private final UserMapper mapper;

    UserController(UserOperationsUseCase userOperationsUseCase, UserMapper userMapper) {
        this.userOperationsUseCase = userOperationsUseCase;
        this.mapper = userMapper;
    }

    @Override
    public ResponseEntity<Void> createUser(User user) {
        if (Utils.isInvalidEmail(user.getEmail())) {
            throw new InvalidParameterValueException("Invalid Email");
        }

        CreateUserCommand command = this.mapper.toCreateUserCommand(user);

        this.userOperationsUseCase.create(command);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

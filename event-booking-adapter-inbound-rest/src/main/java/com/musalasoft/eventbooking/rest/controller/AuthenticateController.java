package com.musalasoft.eventbooking.rest.controller;

import com.musalasoft.eventbooking.core.api.command.AuthenticateCommand;
import com.musalasoft.eventbooking.core.exception.InvalidParameterValueException;
import com.musalasoft.eventbooking.core.portin.AuthenticateUseCase;
import com.musalasoft.eventbooking.rest.api.AuthApi;
import com.musalasoft.eventbooking.rest.mapper.CredentialsMapper;
import com.musalasoft.eventbooking.rest.model.Credentials;
import com.musalasoft.eventbooking.rest.util.Utils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateController implements AuthApi {

    private final AuthenticateUseCase authenticateUseCase;
    private final CredentialsMapper mapper;

    AuthenticateController(AuthenticateUseCase authenticateUseCase, CredentialsMapper mapper) {
        this.authenticateUseCase = authenticateUseCase;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Void> authenticate(Credentials credentials) {
        if (Utils.isInvalidEmail(credentials.getEmail())) {
            throw new InvalidParameterValueException("Invalid Email");
        }

        AuthenticateCommand authenticateCommand = this.mapper.toAuthenticateCommand(credentials);
        String token = this.authenticateUseCase.authenticate(authenticateCommand);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}

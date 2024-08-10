package com.musalasoft.eventbooking.rest.mapper;

import com.musalasoft.eventbooking.core.api.command.AuthenticateCommand;
import com.musalasoft.eventbooking.rest.model.Credentials;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CredentialsMapperTest {

    private CredentialsMapper credentialsMapper;

    @BeforeEach
    void setUp() {
        this.credentialsMapper = Mappers.getMapper(CredentialsMapper.class);
    }

    @Test
    void toAuthenticateCommand_WithNullInput() {
        Assertions.assertNull(this.credentialsMapper.toAuthenticateCommand(null));
    }

    @Test
    void toAuthenticateCommand_WithValidInput() {
        Credentials credentials = new Credentials();
        credentials.setEmail("admin@email.com");
        credentials.setPassword("password");

        AuthenticateCommand expected = AuthenticateCommand.builder()
                                                          .email("admin@email.com")
                                                          .password("password")
                                                          .build();

        AuthenticateCommand actual = this.credentialsMapper.toAuthenticateCommand(credentials);

        Assertions.assertEquals(expected, actual);
    }
}

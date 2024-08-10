package com.musalasoft.eventbooking.rest.mapper;

import com.musalasoft.eventbooking.core.api.command.AuthenticateCommand;
import com.musalasoft.eventbooking.rest.model.Credentials;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

    AuthenticateCommand toAuthenticateCommand(Credentials credentials);
}

package com.musalasoft.eventbooking.rest.mapper;

import com.musalasoft.eventbooking.core.api.command.CreateUserCommand;
import com.musalasoft.eventbooking.rest.model.User;
import org.mapstruct.Mapper;

import java.util.regex.Pattern;

@Mapper(componentModel = "spring")
public interface UserMapper {
    CreateUserCommand toCreateUserCommand(User user);
}

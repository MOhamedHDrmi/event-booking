package com.musalasoft.eventbooking.core.mapper;

import com.musalasoft.eventbooking.core.api.command.CreateUserCommand;
import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.sql.command.CreateUserSqlCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDomainMapper {

    User toUser(CreateUserCommand command);

    CreateUserSqlCommand toCreateSqlUserCommand(User user);
}

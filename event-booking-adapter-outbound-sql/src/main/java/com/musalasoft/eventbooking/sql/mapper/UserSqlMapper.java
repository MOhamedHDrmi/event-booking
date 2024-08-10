package com.musalasoft.eventbooking.sql.mapper;

import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.sql.command.CreateUserSqlCommand;
import com.musalasoft.eventbooking.sql.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSqlMapper {

    UserEntity toUserEntity(CreateUserSqlCommand command);

    User toUser(UserEntity userEntity);
}

package com.musalasoft.eventbooking.core.mapper;

import com.musalasoft.eventbooking.core.api.command.CreateUserCommand;
import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.sql.command.CreateUserSqlCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UserDomainMapperTest {

    private UserDomainMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = Mappers.getMapper(UserDomainMapper.class);
    }

    @Test
    void toUser_WithValidInput() {
        // given
        CreateUserCommand command = CreateUserCommand.builder()
                                                     .name("name")
                                                     .email("email")
                                                     .password("password")
                                                     .build();

        User expected = User.builder()
                            .name("name")
                            .email("email")
                            .password("password")
                            .build();

        // act
        User actual = this.mapper.toUser(command);

        // assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toUser_WithNullInput() {
        Assertions.assertNull(this.mapper.toUser(null));
    }

    @Test
    void toCreateSqlUserCommand_WithValidInput() {
        // given
        User user = User.builder()
                            .name("name")
                            .email("email")
                            .password("password")
                            .build();
        CreateUserSqlCommand expected = CreateUserSqlCommand.builder()
                                                            .name("name")
                                                            .email("email")
                                                            .password("password")
                                                            .build();

        // act
        CreateUserSqlCommand actual = this.mapper.toCreateSqlUserCommand(user);

        // assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toCreateSqlUserCommand_WithNullInput() {
        Assertions.assertNull(this.mapper.toCreateSqlUserCommand(null));
    }
}
package com.musalasoft.eventbooking.rest.mapper;

import com.musalasoft.eventbooking.core.api.command.CreateUserCommand;
import com.musalasoft.eventbooking.rest.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        this.userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void toCreateUserCommand_WithNullInput() {
        Assertions.assertNull(this.userMapper.toCreateUserCommand(null));
    }

    @Test
    void toCreateUserCommand_WithValidInput() {
        User user = new User();
        user.setEmail("admin@email.com");
        user.setName("admin");
        user.setPassword("password");

        CreateUserCommand expected = CreateUserCommand.builder()
                                                     .email("admin@email.com")
                                                     .name("admin")
                                                     .password("password")
                                                     .build();

        CreateUserCommand actual = this.userMapper.toCreateUserCommand(user);

        Assertions.assertEquals(expected, actual);
    }
}
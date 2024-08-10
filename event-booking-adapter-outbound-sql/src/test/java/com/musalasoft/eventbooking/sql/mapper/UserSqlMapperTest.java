package com.musalasoft.eventbooking.sql.mapper;

import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.sql.command.CreateUserSqlCommand;
import com.musalasoft.eventbooking.sql.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UserSqlMapperTest {

    private UserSqlMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = Mappers.getMapper(UserSqlMapper.class);
    }

    @Test
    void toUserEntity_WithValidInput() {
        // given
        CreateUserSqlCommand command = CreateUserSqlCommand.builder()
                                                           .email("email")
                                                           .name("name")
                                                           .password("password")
                                                           .build();
        UserEntity expected = new UserEntity();
        expected.setEmail("email");
        expected.setName("name");
        expected.setPassword("password");

        // act
        UserEntity actual = this.mapper.toUserEntity(command);

        // assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toUserEntity_WithNullInput() {
        Assertions.assertNull(this.mapper.toUserEntity(null));
    }

    @Test
    void toUser_WithValidInput() {
        // given
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("email");
        userEntity.setName("name");
        userEntity.setPassword("password");

        User expected = User.builder()
                        .email("email")
                        .name("name")
                        .password("password")
                        .build();

        // act
        User actual = this.mapper.toUser(userEntity);

        // assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toUser_WithNullInput() {
        Assertions.assertNull(this.mapper.toUser(null));
    }
}
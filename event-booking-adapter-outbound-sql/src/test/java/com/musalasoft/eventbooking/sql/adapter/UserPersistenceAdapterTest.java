package com.musalasoft.eventbooking.sql.adapter;

import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.exception.ResourceNotFoundException;
import com.musalasoft.eventbooking.core.sql.command.CreateUserSqlCommand;
import com.musalasoft.eventbooking.sql.entity.UserEntity;
import com.musalasoft.eventbooking.sql.mapper.UserSqlMapper;
import com.musalasoft.eventbooking.sql.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserPersistenceAdapterTest {

    @Mock
    UserRepository repository;
    @Mock
    UserSqlMapper mapper;
    @InjectMocks
    UserPersistenceAdapter userPersistenceAdapter;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createUser() {
        // given
        CreateUserSqlCommand command = CreateUserSqlCommand.builder()
                                                           .email("email")
                                                           .name("name")
                                                           .password("password")
                                                           .build();
        User user = User.builder()
                        .email("email")
                        .name("name")
                        .password("password")
                        .build();

        // when
        when(this.mapper.toUserEntity(any())).thenReturn(new UserEntity());
        when(this.repository.save(any())).thenReturn(new UserEntity());
        when(this.mapper.toUser(any())).thenReturn(user);

        // act
        User actual = this.userPersistenceAdapter.createUser(command);

        // assert and verify
        Assertions.assertEquals(user, actual);

        verify(this.mapper, times(1)).toUserEntity(any());
        verify(this.mapper, times(1)).toUser(any());
    }

    @Test
    void findByName() {
        // given
        String name = "name";
        User user = User.builder()
                        .email("email")
                        .name("name")
                        .password("password")
                        .build();

        // when
        when(this.repository.findUserEntityByName(anyString())).thenReturn(new UserEntity());
        when(this.mapper.toUser(any())).thenReturn(user);

        // act
        User result = userPersistenceAdapter.findByName(name);

        // assert and verify
        Assertions.assertEquals(user, result);

        verify(this.repository, times(1)).findUserEntityByName(any());
        verify(this.mapper, times(1)).toUser(any());
    }

    @Test
    void findByEmailAndPassword() {
        // given
        String email = "email";
        String password = "password";
        User user = User.builder()
                        .email("email")
                        .name("name")
                        .password("password")
                        .build();

        // when
        when(repository.findUserEntityByName(anyString())).thenReturn(new UserEntity());
        when(this.mapper.toUser(any())).thenReturn(user);

        // act
        User result = userPersistenceAdapter.findByEmailAndPassword(email, password);

        // assert and verify
        Assertions.assertEquals(user, result);

        verify(this.repository, times(1)).findUserEntityByEmailAndPassword(anyString(), anyString());
        verify(this.mapper, times(1)).toUser(any());
    }

    @Test
    void findByIdWithValidUser() {
        // given
        Long userId = 1L;
        User user = User.builder()
                        .userId(1L)
                        .email("email")
                        .name("name")
                        .password("password")
                        .build();

        // when
        when(this.repository.findById(anyLong())).thenReturn(Optional.of(new UserEntity()));
        when(this.mapper.toUser(any())).thenReturn(user);

        // act
        User result = userPersistenceAdapter.findById(userId);

        // assert and verify
        Assertions.assertEquals(user, result);

        verify(this.repository, times(1)).findById(any());
        verify(this.mapper, times(1)).toUser(any());
    }

    @Test
    void findByIdWithInvalidUser() {
        // given
        Long userId = 1L;
        User user = User.builder()
                        .userId(1L)
                        .email("email")
                        .name("name")
                        .password("password")
                        .build();

        // when
        when(this.repository.findById(anyLong())).thenReturn(Optional.empty());
        when(this.mapper.toUser(any())).thenReturn(user);


        // assert and verify
        Assertions.assertThrows(ResourceNotFoundException.class,
                                () -> userPersistenceAdapter.findById(userId),
                                "Can't Find User with Id: " + userId);

        verify(this.repository, times(1)).findById(any());
    }
}
package com.musalasoft.eventbooking.core.adapter;

import com.musalasoft.eventbooking.core.api.command.CreateUserCommand;
import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.mapper.UserDomainMapper;
import com.musalasoft.eventbooking.core.portout.UserProjector;
import com.musalasoft.eventbooking.core.sql.command.CreateUserSqlCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserAdapterTest {

    @Mock
    UserProjector projector;
    @Mock
    UserDomainMapper mapper;
    @InjectMocks
    UserAdapter userAdapter;

    private  AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void create() {
        // given
        User user = User.builder()
                        .name("name")
                        .email("email")
                        .password("password")
                        .userId(1L)
                        .build();
        CreateUserSqlCommand command = CreateUserSqlCommand.builder()
                                                           .name("name")
                                                           .email("email")
                                                           .password("password")
                                                           .build();
        CreateUserCommand createUserCommand = CreateUserCommand.builder()
                                                               .name("name")
                                                               .email("email")
                                                               .password("password")
                                                               .build();

        // when
        when(mapper.toUser(any())).thenReturn(user);
        when(projector.createUser(any())).thenReturn(user);
        when(mapper.toCreateSqlUserCommand(any())).thenReturn(command);

        // act
        userAdapter.create(createUserCommand);

        // assert and verify
        verify(this.mapper, times(1)).toUser(any());
        verify(this.mapper, times(1)).toCreateSqlUserCommand(any());
        verify(this.projector, times(1)).createUser(any());
    }

    @Test
    void findByName() {
        // given
        String name = "name";
        User user = User.builder()
                        .name("name")
                        .email("email")
                        .password("password")
                        .userId(1L)
                        .build();

        // when
        when(projector.findByName(anyString())).thenReturn(user);

        // act
        User result = userAdapter.findByName(name);

        // assert
        Assertions.assertEquals(user, result);
        verify(this.projector, times(1)).findByName(anyString());
    }

    @Test
    void findById() {
        // given
        Long userId = 1L;
        User user = User.builder()
                        .userId(userId)
                        .name("name")
                        .email("email")
                        .password("password")
                        .userId(1L)
                        .build();

        // when
        when(projector.findById(anyLong())).thenReturn(user);

        // act
        User result = userAdapter.findById(userId);

        // assert
        Assertions.assertEquals(user, result);
        verify(this.projector, times(1)).findById(anyLong());
    }
}
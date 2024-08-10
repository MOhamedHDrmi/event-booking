package com.musalasoft.eventbooking.core.adapter;

import com.musalasoft.eventbooking.core.api.command.AuthenticateCommand;
import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.exception.InvalidCredentialsException;
import com.musalasoft.eventbooking.core.portout.UserProjector;
import com.musalasoft.eventbooking.core.security.util.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthenticationAdapterTest {

    @Mock
    JwtUtil jwtUtil;
    @Mock
    UserProjector projector;
    @InjectMocks
    AuthenticationAdapter authenticationAdapter;

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
    void authenticate() {
        // given
        AuthenticateCommand command = AuthenticateCommand.builder()
                                                         .password("password")
                                                         .email("email")
                                                         .build();
        User user = User.builder()
                        .name("name")
                        .password("password")
                        .email("email")
                        .build();

        String expected = "generateTokenResponse";

        // when
        when(projector.findByEmailAndPassword(anyString(), anyString())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn("generateTokenResponse");

        // act
        String actual = authenticationAdapter.authenticate(command);

        // assert and verify
        Assertions.assertEquals(expected, actual);

        verify(this.projector, times(1)).findByEmailAndPassword(anyString(), anyString());
        verify(this.jwtUtil, times(1)).generateToken(any(UserDetails.class));
    }

    @Test
    void authenticate_WithInvalidUser() {
        // given
        AuthenticateCommand command = AuthenticateCommand.builder()
                                                         .password("password")
                                                         .email("email")
                                                         .build();

        when(projector.findByEmailAndPassword(anyString(), anyString())).thenReturn(null);

        Assertions.assertThrows(InvalidCredentialsException.class,
                                () -> this.authenticationAdapter.authenticate(command),
                                "Invalid Credentials");
    }
}
package com.musalasoft.eventbooking.core.adapter;

import com.musalasoft.eventbooking.core.api.command.AuthenticateCommand;
import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.exception.InvalidCredentialsException;
import com.musalasoft.eventbooking.core.portin.AuthenticateUseCase;
import com.musalasoft.eventbooking.core.portout.UserProjector;
import com.musalasoft.eventbooking.core.security.CustomUserDetails;
import com.musalasoft.eventbooking.core.security.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationAdapter implements AuthenticateUseCase {

    private final JwtUtil jwtUtil;
    private final UserProjector projector;

    AuthenticationAdapter(JwtUtil jwtUtil, UserProjector projector) {
        this.jwtUtil = jwtUtil;
        this.projector = projector;
    }

    @Override
    public String authenticate(AuthenticateCommand authenticateCommand) throws RuntimeException {
        User user = this.projector.findByEmailAndPassword(authenticateCommand.getEmail(), authenticateCommand.getPassword());

        if (user == null) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }

        UserDetails userDetails = CustomUserDetails.builder()
                                                   .name(user.getName())
                                                   .email(user.getEmail())
                                                   .password(user.getPassword())
                                                   .build();

        log.info("Generate token for authenticated user: {}", user.getName());

        return this.jwtUtil.generateToken(userDetails);
    }
}

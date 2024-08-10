package com.musalasoft.eventbooking.core.security;

import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.portin.UserOperationsUseCase;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserOperationsUseCase userOperationsUseCase;

    CustomUserDetailsService(UserOperationsUseCase userOperationsUseCase) {
        this.userOperationsUseCase = userOperationsUseCase;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userOperationsUseCase.findByName(username);

        return CustomUserDetails.builder()
                                .name(user.getName())
                                .password(user.getPassword())
                                .email(user.getEmail())
                                .build();
    }
}

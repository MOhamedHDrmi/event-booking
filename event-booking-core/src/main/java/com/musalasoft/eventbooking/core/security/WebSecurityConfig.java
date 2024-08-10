package com.musalasoft.eventbooking.core.security;

import com.musalasoft.eventbooking.core.exception.APIAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final APIAuthenticationEntryPoint unauthorizedAccessHandler;

    WebSecurityConfig(JwtRequestFilter jwtRequestFilter, APIAuthenticationEntryPoint unauthorizedAccessHandler) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.unauthorizedAccessHandler = unauthorizedAccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers(HttpMethod.GET, "/events").permitAll()
            .antMatchers(HttpMethod.POST, "/users", "/auth").permitAll()
            .antMatchers("/events/*").authenticated()
            .anyRequest().authenticated()
            .and().exceptionHandling().authenticationEntryPoint(unauthorizedAccessHandler)
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);;
        return http.build();
    }
}

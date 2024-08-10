package com.musalasoft.eventbooking.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.musalasoft.eventbooking.rest.model.Credentials;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class AuthenticationControllerIT extends SetUpHelper {

    @Test
    void authentication_WithValidUser_Return200AndToken() throws JsonProcessingException {
        Credentials credentials = new Credentials();
        credentials.setEmail("admin@gmail.com");
        credentials.setPassword("12345678");

        webClient.post()
                 .uri("/auth").contentType(MediaType.APPLICATION_JSON)
                 .bodyValue(objectMapper.writeValueAsString(credentials))
                 .exchange()
                 .expectStatus().isOk()
                 .expectHeader().exists("Authorization");
    }

    @Test
    void authentication_WithInvalidUser_Return401() throws JsonProcessingException {
        Credentials credentials = new Credentials();
        credentials.setEmail("user@gmail.com");
        credentials.setPassword("11110000");

        webClient.post()
                 .uri("/auth").contentType(MediaType.APPLICATION_JSON)
                 .bodyValue(objectMapper.writeValueAsString(credentials))
                 .exchange().expectStatus().isUnauthorized();
    }

    @Test
    void authentication_WithInvalidParameter_Return400() throws JsonProcessingException {
        Credentials credentials = new Credentials();
        credentials.setEmail("user2gmail.com");
        credentials.setPassword("11110000");

        webClient.post()
                 .uri("/auth").contentType(MediaType.APPLICATION_JSON)
                 .bodyValue(objectMapper.writeValueAsString(credentials))
                 .exchange().expectStatus().isBadRequest();
    }
}

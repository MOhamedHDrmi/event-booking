package com.musalasoft.eventbooking.application.integrationtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.musalasoft.eventbooking.rest.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class UserControllerIT extends SetUpHelper {

    @Test
    void create_WithValidInputs_Return200() throws JsonProcessingException {
        User user = new User();
        user.setName("name");
        user.setPassword("password");
        user.setEmail("email@email.com");

        webClient.post()
                 .uri("/users").contentType(MediaType.APPLICATION_JSON)
                 .bodyValue(objectMapper.writeValueAsString(user))
                 .exchange()
                 .expectStatus().isCreated();
    }

    @Test
    void create_WithInvalidParameters_Return400() throws JsonProcessingException {
        User user = new User();
        user.setName("name");
        user.setPassword("password");
        user.setEmail("email2email.com");

        webClient.post()
                 .uri("/users").contentType(MediaType.APPLICATION_JSON)
                 .bodyValue(objectMapper.writeValueAsString(user))
                 .exchange().expectStatus().isBadRequest();
    }
}

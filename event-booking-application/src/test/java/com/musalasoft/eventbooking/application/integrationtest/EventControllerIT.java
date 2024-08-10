package com.musalasoft.eventbooking.application.integrationtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.musalasoft.eventbooking.core.security.CustomUserDetails;
import com.musalasoft.eventbooking.core.security.util.JwtUtil;
import com.musalasoft.eventbooking.rest.model.Category;
import com.musalasoft.eventbooking.rest.model.EventRequestDTO;
import com.musalasoft.eventbooking.rest.model.EventResponseDTO;
import com.musalasoft.eventbooking.rest.model.EventTicketResponseDTO;
import com.musalasoft.eventbooking.rest.model.TicketRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

class EventControllerIT extends SetUpHelper {

    private String bearerToken;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        UserDetails userDetails = CustomUserDetails.builder()
                                                   .name("admin")
                                                   .password("12345678")
                                                   .email("admin@gmail.com")
                                                   .build();
        this.bearerToken = "Bearer " + this.jwtUtil.generateToken(userDetails);
    }

    @Test
    void createEvent_WithAuthorization_Return200() throws JsonProcessingException {
        EventRequestDTO eventRequestDTO = new EventRequestDTO();
        eventRequestDTO.setCategory(Category.CONFERENCE);
        eventRequestDTO.setDate(LocalDate.of(2024, Month.FEBRUARY, 10));
        eventRequestDTO.setName("name");
        eventRequestDTO.setDescription("description");
        eventRequestDTO.setAvailableAttendeesCount(100);

        webClient.post()
                 .uri("/events")
                 .contentType(MediaType.APPLICATION_JSON)
                 .bodyValue(this.objectMapper.writeValueAsString(eventRequestDTO))
                 .header("Authorization", bearerToken)
                 .exchange()
                 .expectStatus().isCreated();
    }

    @Test
    void createEvent_WithOutAuthorization_Return401() throws JsonProcessingException {
        EventRequestDTO eventRequestDTO = new EventRequestDTO();

        webClient.post()
                 .uri("/events")
                 .contentType(MediaType.APPLICATION_JSON)
                 .bodyValue(this.objectMapper.writeValueAsString(eventRequestDTO))
                 .exchange()
                 .expectStatus().isUnauthorized();
    }

    @Test
    void cancelEventTicket_WithAuthorization_Return203() {
        webClient.delete()
                 .uri("events/tickets/1")
                 .header("Authorization", bearerToken)
                 .exchange()
                 .expectStatus().isNoContent();
    }

    @Test
    void cancelEventTicket_WithOutAuthorization_Return203() {
        webClient.delete()
                 .uri("events/tickets/1")
                 .exchange()
                 .expectStatus().isUnauthorized();
    }

    @Test
    void getAll_WithFilter_Return200AndDataMatchFilter() {
        webClient.get()
                 .uri(uriBuilder -> uriBuilder
                     .path("/events")
                     .queryParam("category", "Game")
                     .build())
                 .exchange()
                 .expectStatus().isOk()
                 .expectBodyList(EventResponseDTO.class)
                 .consumeWith(response -> {
                     List<EventResponseDTO> responseDTOList = response.getResponseBody();

                     Assertions.assertNotNull(responseDTOList);
                     Assertions.assertEquals(3, responseDTOList.size());

                     responseDTOList.forEach(responseDTO -> Assertions.assertEquals(Category.GAME.getValue(), responseDTO.getCategory().getValue()));
                 });
    }

    @Test
    void getAll_WithOutFilter_Return200AndAllData() {
        webClient.get()
                 .uri("/events")
                 .exchange()
                 .expectStatus().isOk()
                 .expectBodyList(EventResponseDTO.class)
                 .consumeWith(response -> {
                     List<EventResponseDTO> responseDTOList = response.getResponseBody();

                     Assertions.assertNotNull(responseDTOList);
                     Assertions.assertEquals(10, responseDTOList.size());
                 });
    }

    @Test
    void getAllBooked_WithAuthorization_Return200AndAssociateData() {
        webClient.get()
                 .uri("/events/tickets")
                 .header("Authorization", bearerToken)
                 .exchange()
                 .expectStatus().isOk()
                 .expectBodyList(EventTicketResponseDTO.class)
                 .consumeWith(response -> {
                     List<EventTicketResponseDTO> responseDTOList = response.getResponseBody();

                     Assertions.assertNotNull(responseDTOList);
                     Assertions.assertFalse(responseDTOList.isEmpty());
                 });
    }

    @Test
    void getAllBooked_WithOutAuthorization_Return401() {
        webClient.get()
                 .uri("/events/tickets")
                 .exchange()
                 .expectStatus().isUnauthorized();
    }

    @Test
    void reserveEventTicket_WithAuthorization_Return201() throws JsonProcessingException {
        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setAttendeesCount(100);

        webClient.post()
                 .uri("/events/2/tickets")
                 .contentType(MediaType.APPLICATION_JSON)
                 .bodyValue(this.objectMapper.writeValueAsString(ticketRequest))
                 .header("Authorization", bearerToken)
                 .exchange()
                 .expectStatus().isCreated();
    }

    @Test
    void reserveEventTicket_WithOutAuthorization_Return401() throws JsonProcessingException {
        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setAttendeesCount(100);

        webClient.post()
                 .uri("/events/3/tickets")
                 .contentType(MediaType.APPLICATION_JSON)
                 .bodyValue(this.objectMapper.writeValueAsString(ticketRequest))
                 .exchange()
                 .expectStatus().isUnauthorized();
    }
}

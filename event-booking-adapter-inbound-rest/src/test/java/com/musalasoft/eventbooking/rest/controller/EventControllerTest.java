package com.musalasoft.eventbooking.rest.controller;

import com.musalasoft.eventbooking.core.api.command.CancelTicketCommand;
import com.musalasoft.eventbooking.core.api.command.CreateEventCommand;
import com.musalasoft.eventbooking.core.api.command.ReserveEventTicketCommand;
import com.musalasoft.eventbooking.core.api.filter.EventFilter;
import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.domain.Ticket;
import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.domain.enums.Category;
import com.musalasoft.eventbooking.core.portin.EventUseCase;
import com.musalasoft.eventbooking.core.portin.TicketReservationUseCase;
import com.musalasoft.eventbooking.rest.mapper.EventMapper;
import com.musalasoft.eventbooking.rest.mapper.TicketReservationMapper;
import com.musalasoft.eventbooking.rest.model.CreateEvent201Response;
import com.musalasoft.eventbooking.rest.model.EventRequestDTO;
import com.musalasoft.eventbooking.rest.model.EventResponseDTO;
import com.musalasoft.eventbooking.rest.model.EventTicketResponseDTO;
import com.musalasoft.eventbooking.rest.model.TicketRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class EventControllerTest {

    @Mock
    EventUseCase eventUseCase;
    @Mock
    TicketReservationUseCase ticketReservationUseCase;
    @Mock
    EventMapper mapper;
    @Mock
    TicketReservationMapper ticketReservationMapper;
    @InjectMocks
    EventController eventController;

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
    void cancelEventTicket() {
        // given
        CancelTicketCommand command = CancelTicketCommand.builder()
                                                         .ticketId(1L)
                                                         .build();

        // when
        when(this.ticketReservationMapper.toCancelReservationCommand(anyLong())).thenReturn(null);
        doNothing().when(this.ticketReservationUseCase).cancelEventReservation(command);

        // act
        ResponseEntity<Void> response = eventController.cancelEventTicket(1L);

        // assert
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void createEvent() {
        // given
        Event event = Event.builder()
                           .category(Category.CONCERT)
                           .eventId(10L)
                           .description("description")
                           .name("name")
                           .date(LocalDate.of(2024, 2, 10))
                           .availableAttendeesCount(100)
                           .build();
        CreateEvent201Response response = new CreateEvent201Response();
        response.setEventId(10L);

        // when
        when(this.mapper.toCreateEventCommand(any())).thenReturn(CreateEventCommand.builder().build());
        when(this.eventUseCase.createEvent(any())).thenReturn(event);
        when(this.mapper.toCreateEvent201Response(any())).thenReturn(response);

        // act
        ResponseEntity<CreateEvent201Response> actual = eventController.createEvent(new EventRequestDTO());

        // assert
        Assertions.assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        Assertions.assertEquals(response, actual.getBody());
    }

    @Test
    void getAll() {

        // when
        when(this.eventUseCase.findAll(any())).thenReturn(List.of(Event.builder().build()));
        when(this.mapper.toEventFilter(anyString(), any(), any(), any())).thenReturn(EventFilter.builder().build());
        when(this.mapper.toEventResponseDTO(any())).thenReturn(new EventResponseDTO());

        // act
        ResponseEntity<List<EventResponseDTO>> result = eventController.getAll("name", LocalDate.of(2024, Month.FEBRUARY, 17),
                                                                               LocalDate.of(2024, Month.FEBRUARY, 17), "Game");

        // assert
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertFalse(result.getBody().isEmpty());
    }

    @Test
    void getAllBooked() {
        // given
        Ticket ticket = Ticket.builder()
                              .event(Event.builder().build())
                              .user(User.builder().build())
                              .ticketId(1L)
                              .attendanceCount(100)
                              .build();

        // when
        when(this.ticketReservationUseCase.getAllBooked()).thenReturn(List.of(ticket));
        when(this.ticketReservationMapper.toEventTicketResponseDTO(any())).thenReturn(new EventTicketResponseDTO());

        // act
        ResponseEntity<List<EventTicketResponseDTO>> result = eventController.getAllBooked();

        // assert
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertFalse(result.getBody().isEmpty());
    }

    @Test
    void reserveEventTicket() {
        // given


        // when
        when(ticketReservationMapper.toReserveEventCommand(anyLong(), any())).thenReturn(ReserveEventTicketCommand.builder().build());
        doNothing().when(this.ticketReservationUseCase).reserveEvent(any());

        ResponseEntity<Void> response = eventController.reserveEventTicket(1L, new TicketRequest());

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
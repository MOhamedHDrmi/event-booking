package com.musalasoft.eventbooking.rest.mapper;

import com.musalasoft.eventbooking.core.api.command.CancelTicketCommand;
import com.musalasoft.eventbooking.core.api.command.ReserveEventTicketCommand;
import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.domain.Ticket;
import com.musalasoft.eventbooking.rest.model.EventTicketResponseDTO;
import com.musalasoft.eventbooking.rest.model.TicketRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class TicketTicketMapperTest {

    private TicketReservationMapper ticketReservationMapper;

    @BeforeEach
    void setUp() {
        this.ticketReservationMapper = Mappers.getMapper(TicketReservationMapper.class);
    }

    @Test
    void toReserveEventCommand_WithValidInput() {
        ReserveEventTicketCommand expected = ReserveEventTicketCommand.builder()
                                                                      .eventId(10L)
                                                                      .attendeesCount(100)
                                                                      .build();

        TicketRequest request = new TicketRequest();
        request.setAttendeesCount(100);

        ReserveEventTicketCommand actual = this.ticketReservationMapper.toReserveEventCommand(10L, request);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toReserveEventCommand_WithNullInput() {
        Assertions.assertNull(this.ticketReservationMapper.toReserveEventCommand(null, null));
    }

    @Test
    void toCancelReservationCommand_WithValidInput() {
        CancelTicketCommand expected = CancelTicketCommand.builder()
                                                          .ticketId(10L)
                                                          .build();

        CancelTicketCommand actual = this.ticketReservationMapper.toCancelReservationCommand(10L);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toCancelReservationCommand_WithNullInput() {
        Assertions.assertNull(this.ticketReservationMapper.toCancelReservationCommand(null));
    }

    @Test
    void toReservedEventResponseDTO_WithValidInput() {
        EventTicketResponseDTO expected = new EventTicketResponseDTO();
        expected.setTicketId(5L);
        expected.setAttendanceCount(100);
        expected.setEventId(10L);

        Ticket ticket = Ticket.builder()
                              .ticketId(5L)
                              .attendanceCount(100)
                              .event(Event.builder()
                                                                   .eventId(10L)
                                                                   .build())
                              .build();

        EventTicketResponseDTO actual = this.ticketReservationMapper.toEventTicketResponseDTO(ticket);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toReservedEventResponseDTO_WithNullInput() {
        Assertions.assertNull(this.ticketReservationMapper.toEventTicketResponseDTO(null));
    }

    @Test
    void GetEventId_WithValidInput() {
        Long result = ticketReservationMapper.getEventId(new Ticket(1L, 0, null, Event.builder().eventId(10L).build()));

        Assertions.assertEquals(10L, result);
    }

    @Test
    void GetEventId_WithNullInput() {
        Long result = ticketReservationMapper.getEventId(new Ticket(1L, 0, null, null));

        Assertions.assertNull(result);
    }
}
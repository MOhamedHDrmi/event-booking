package com.musalasoft.eventbooking.rest.mapper;

import com.musalasoft.eventbooking.core.api.command.CancelTicketCommand;
import com.musalasoft.eventbooking.core.api.command.ReserveEventTicketCommand;
import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.domain.Ticket;
import com.musalasoft.eventbooking.rest.model.EventTicketResponseDTO;
import com.musalasoft.eventbooking.rest.model.TicketRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketReservationMapper {

    @Mapping(target = "attendeesCount", source = "request.attendeesCount")
    ReserveEventTicketCommand toReserveEventCommand(Long eventId, TicketRequest request);

    CancelTicketCommand toCancelReservationCommand(Long ticketId);

    @Mapping(target = "eventId", expression = "java(this.getEventId(ticket))")
    EventTicketResponseDTO toEventTicketResponseDTO(Ticket ticket);

    default Long getEventId(Ticket ticket) {
        Event event = ticket.getEvent();
        return event == null ? null : event.getEventId();
    }
}

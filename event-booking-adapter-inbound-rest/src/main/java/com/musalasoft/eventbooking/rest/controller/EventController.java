package com.musalasoft.eventbooking.rest.controller;

import com.musalasoft.eventbooking.core.api.command.CancelTicketCommand;
import com.musalasoft.eventbooking.core.api.command.CreateEventCommand;
import com.musalasoft.eventbooking.core.api.command.ReserveEventTicketCommand;
import com.musalasoft.eventbooking.core.api.filter.EventFilter;
import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.domain.Ticket;
import com.musalasoft.eventbooking.core.portin.EventUseCase;
import com.musalasoft.eventbooking.core.portin.TicketReservationUseCase;
import com.musalasoft.eventbooking.rest.api.EventsApi;
import com.musalasoft.eventbooking.rest.mapper.EventMapper;
import com.musalasoft.eventbooking.rest.mapper.TicketReservationMapper;
import com.musalasoft.eventbooking.rest.model.CreateEvent201Response;
import com.musalasoft.eventbooking.rest.model.EventRequestDTO;
import com.musalasoft.eventbooking.rest.model.EventResponseDTO;
import com.musalasoft.eventbooking.rest.model.EventTicketResponseDTO;
import com.musalasoft.eventbooking.rest.model.TicketRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class EventController implements EventsApi {

    private final EventUseCase eventUseCase;
    private final TicketReservationUseCase ticketReservationUseCase;
    private final EventMapper mapper;
    private final TicketReservationMapper ticketReservationMapper;

    EventController(EventUseCase eventUseCase, TicketReservationUseCase ticketReservationUseCase, EventMapper mapper,
        TicketReservationMapper ticketReservationMapper) {
        this.eventUseCase = eventUseCase;
        this.ticketReservationUseCase = ticketReservationUseCase;
        this.mapper = mapper;
        this.ticketReservationMapper = ticketReservationMapper;
    }

    @Override
    public ResponseEntity<Void> cancelEventTicket(Long ticketId) {
        CancelTicketCommand command = this.ticketReservationMapper.toCancelReservationCommand(ticketId);

        this.ticketReservationUseCase.cancelEventReservation(command);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<CreateEvent201Response> createEvent(EventRequestDTO event) {
        CreateEventCommand createEventCommand = this.mapper.toCreateEventCommand(event);

        Event createdEvent = this.eventUseCase.createEvent(createEventCommand);

        return new ResponseEntity<>(this.mapper.toCreateEvent201Response(createdEvent), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<EventResponseDTO>> getAll(String name, LocalDate startDate, LocalDate endDate, String category) {
        EventFilter filter = this.mapper.toEventFilter(name, startDate, endDate, category);

        List<Event> eventList = this.eventUseCase.findAll(filter);

        List<EventResponseDTO> responseDTOList = Stream.ofNullable(eventList)
                                                       .flatMap(Collection::stream)
                                                       .map(this.mapper::toEventResponseDTO)
                                                       .collect(Collectors.toList());

        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<EventTicketResponseDTO>> getAllBooked() {
        List<Ticket> tickets = this.ticketReservationUseCase.getAllBooked();
        List<EventTicketResponseDTO> responseDTOList = tickets.stream()
                                                              .map(ticketReservationMapper::toEventTicketResponseDTO)
                                                              .collect(Collectors.toList());
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> reserveEventTicket(Long eventId, TicketRequest ticketRequest) {
        ReserveEventTicketCommand reserveEventTicketCommand = this.ticketReservationMapper.toReserveEventCommand(eventId, ticketRequest);

        this.ticketReservationUseCase.reserveEvent(reserveEventTicketCommand);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

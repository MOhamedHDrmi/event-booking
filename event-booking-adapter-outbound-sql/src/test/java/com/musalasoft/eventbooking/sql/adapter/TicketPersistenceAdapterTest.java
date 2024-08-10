package com.musalasoft.eventbooking.sql.adapter;

import com.musalasoft.eventbooking.core.api.filter.ReservationFilter;
import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.domain.Ticket;
import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.exception.InvalidParameterValueException;
import com.musalasoft.eventbooking.core.sql.command.CancelTicketSqlCommand;
import com.musalasoft.eventbooking.core.sql.command.ReserveTicketSqlCommand;
import com.musalasoft.eventbooking.sql.entity.EventEntity;
import com.musalasoft.eventbooking.sql.entity.TicketEntity;
import com.musalasoft.eventbooking.sql.mapper.TicketReservationSqlMapper;
import com.musalasoft.eventbooking.sql.repository.EventRepository;
import com.musalasoft.eventbooking.sql.repository.TicketRepository;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TicketPersistenceAdapterTest {

    @Mock
    TicketRepository ticketRepository;
    @Mock
    EventRepository eventRepository;
    @Mock
    TicketReservationSqlMapper mapper;
    @InjectMocks
    TicketPersistenceAdapter ticketPersistenceAdapter;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void reserveEvent_WithValidInput() {
        // given
        EventEntity eventEntity = new EventEntity();
        eventEntity.setAvailableAttendeesCount(25);

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setAttendanceCount(10);

        TicketEntity reservedEntity = new TicketEntity();
        reservedEntity.setAttendanceCount(5);

        ReserveTicketSqlCommand command = ReserveTicketSqlCommand.builder()
                                                                 .eventId(1L)
                                                                 .userId(2L)
                                                                 .attendeesCount(10)
                                                                 .build();

        // when
        when(this.mapper.toReservationEntity(any())).thenReturn(ticketEntity);
        when(this.ticketRepository.findByEvent_EventId(anyLong())).thenReturn(List.of(reservedEntity));
        when(this.eventRepository.findById(anyLong())).thenReturn(Optional.of(eventEntity));

        // act
        this.ticketPersistenceAdapter.reserveEventTicket(command);

        // verify
        verify(this.mapper, times(1)).toReservationEntity(any());
        verify(this.ticketRepository, times(1)).findByEvent_EventId(anyLong());
        verify(this.eventRepository, times(1)).findById(any());
    }

    @Test
    void reserveEvent_WithInvalidInput() {
        // given
        EventEntity eventEntity = new EventEntity();
        eventEntity.setAvailableAttendeesCount(15);

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setAttendanceCount(10);

        TicketEntity reservedEntity = new TicketEntity();
        reservedEntity.setAttendanceCount(8);

        ReserveTicketSqlCommand command = ReserveTicketSqlCommand.builder()
                                                                 .eventId(1L)
                                                                 .userId(2L)
                                                                 .attendeesCount(4)
                                                                 .build();

        // when
        when(this.mapper.toReservationEntity(any())).thenReturn(ticketEntity);
        when(this.ticketRepository.findByEvent_EventId(anyLong())).thenReturn(List.of(reservedEntity));
        when(this.eventRepository.findById(anyLong())).thenReturn(Optional.of(eventEntity));

        // assert and verify
        Assertions.assertThrows(InvalidParameterValueException.class,
                                () -> this.ticketPersistenceAdapter.reserveEventTicket(command),
                                "Maximum attendance reached for this event.");

        verify(this.mapper, times(1)).toReservationEntity(any());
        verify(this.ticketRepository, times(1)).findByEvent_EventId(anyLong());
        verify(this.eventRepository, times(1)).findById(any());
    }

    @Test
    void deleteEventReservation() {
        // given
        CancelTicketSqlCommand command = CancelTicketSqlCommand.builder()
                                                               .ticketId(1L)
                                                               .build();

        // when
        doNothing().when(this.ticketRepository).deleteById(any());

        // act
        this.ticketPersistenceAdapter.deleteEventReservationTicket(command);

        // verify
        verify(this.ticketRepository, times(1)).deleteById(any());
    }

    @Test
    void getAllBooked() {
        // given
        Ticket ticket = Ticket.builder()
                              .attendanceCount(10)
                              .ticketId(5L)
                              .event(Event.builder().build())
                              .user(User.builder().build())
                              .build();
        Long userId = 1L;

        // when
        when(this.ticketRepository.findByUser_UserId(anyLong())).thenReturn(List.of(new TicketEntity()));
        when(this.mapper.toReservation(any())).thenReturn(ticket);

        // act
        List<Ticket> result = ticketPersistenceAdapter.getAllBooked(userId);

        // assert and verify
        Assertions.assertEquals(List.of(ticket), result);

        verify(this.mapper, times(1)).toReservation(any());
        verify(this.ticketRepository, times(1)).findByUser_UserId(anyLong());
    }

    @Test
    void findAllWithValidFilter() {
        // given
        ReservationFilter filter = ReservationFilter.builder()
                                                    .reservationDate(LocalDate.of(2024, Month.FEBRUARY, 10))
                                                    .build();
        Ticket ticket = Ticket.builder()
                              .attendanceCount(10)
                              .ticketId(5L)
                              .event(Event.builder().build())
                              .user(User.builder().build())
                              .build();

        // when
        when(this.ticketRepository.findAll(any(Predicate.class))).thenReturn(List.of(new TicketEntity()));
        when(this.mapper.toReservation(any())).thenReturn(ticket);

        // act
        List<Ticket> result = ticketPersistenceAdapter.findAll(filter);

        // assert and verify
        Assertions.assertEquals(List.of(ticket), result);

        verify(this.ticketRepository, times(1)).findAll(any(Predicate.class));
        verify(this.mapper, times(1)).toReservation(any());
    }

    @Test
    void findAllWithInvalidFilter() {
        // given
        Ticket ticket = Ticket.builder()
                              .attendanceCount(10)
                              .ticketId(5L)
                              .event(Event.builder().build())
                              .user(User.builder().build())
                              .build();

        // when
        when(this.ticketRepository.findAll()).thenReturn(List.of(new TicketEntity()));
        when(this.mapper.toReservation(any())).thenReturn(ticket);

        // act
        List<Ticket> result = ticketPersistenceAdapter.findAll(null);

        // assert and verify
        Assertions.assertEquals(List.of(ticket), result);

        verify(this.ticketRepository, times(1)).findAll();
        verify(this.mapper, times(1)).toReservation(any());
    }
}
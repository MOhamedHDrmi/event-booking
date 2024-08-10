package com.musalasoft.eventbooking.core.adapter;

import com.musalasoft.eventbooking.core.api.command.CancelTicketCommand;
import com.musalasoft.eventbooking.core.api.command.ReserveEventTicketCommand;
import com.musalasoft.eventbooking.core.api.filter.ReservationFilter;
import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.domain.Ticket;
import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.domain.enums.Category;
import com.musalasoft.eventbooking.core.mapper.TicketReservationDomainMapper;
import com.musalasoft.eventbooking.core.portout.TicketProjector;
import com.musalasoft.eventbooking.core.security.CustomUserDetails;
import com.musalasoft.eventbooking.core.sql.command.CancelTicketSqlCommand;
import com.musalasoft.eventbooking.core.sql.command.ReserveTicketSqlCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TicketAdapterTest {

    @Mock
    TicketReservationDomainMapper domainMapper;
    @Mock
    UserAdapter userAdapter;
    @Mock
    TicketProjector ticketProjector;
    @Mock
    Authentication authentication;
    @Mock
    SecurityContext securityContext;
    @InjectMocks
    TicketReservationAdapter reservationAdapter;

    private  AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void reserveEvent() {
        // given
        UserDetails userDetails = CustomUserDetails.builder()
                                                   .name("name")
                                                   .email("email")
                                                   .password("password")
                                                   .build();
        User user = User.builder()
                        .userId(1L)
                        .name("name")
                        .email("email")
                        .password("password")
                        .build();
        ReserveTicketSqlCommand command = ReserveTicketSqlCommand.builder()
                                                                 .eventId(1L)
                                                                 .userId(1L)
                                                                 .attendeesCount(20)
                                                                 .build();
        ReserveEventTicketCommand reserveEventTicketCommand = ReserveEventTicketCommand.builder()
                                                                                       .eventId(1L)
                                                                                       .attendeesCount(20)
                                                                                       .build();

        // when
        when(this.securityContext.getAuthentication()).thenReturn(this.authentication);
        when(this.authentication.getPrincipal()).thenReturn(userDetails);
        when(this.domainMapper.toReserveSqlEventCommand(anyLong(), any())).thenReturn(command);
        when(this.userAdapter.findByName(anyString())).thenReturn(user);

        // act
        reservationAdapter.reserveEvent(reserveEventTicketCommand);

        // assert and verify
        verify(this.securityContext, times(1)).getAuthentication();
        verify(this.authentication, times(1)).getPrincipal();
        verify(this.domainMapper, times(1)).toReserveSqlEventCommand(anyLong(), any());
        verify(this.userAdapter, times(1)).findByName(anyString());
    }

    @Test
    void cancelEventReservation() {
        // given
        CancelTicketCommand command = CancelTicketCommand.builder()
                                                         .ticketId(1L)
                                                         .build();
        CancelTicketSqlCommand cancelTicketSqlCommand = CancelTicketSqlCommand.builder()
                                                                              .ticketId(1L)
                                                                              .build();

        //  when
        when(domainMapper.toCancelSqlReservationCommand(any())).thenReturn(cancelTicketSqlCommand);

        // act
        reservationAdapter.cancelEventReservation(command);

        // assert and verify
        verify(this.domainMapper, times(1)).toCancelSqlReservationCommand(any());
    }

    @Test
    void getAllBooked() {
        // given
        UserDetails userDetails = CustomUserDetails.builder()
                                                   .name("name")
                                                   .email("email")
                                                   .password("password")
                                                   .build();
        User user = User.builder()
                        .userId(1L)
                        .name("name")
                        .email("email")
                        .password("password")
                        .build();
        Event event = Event.builder()
                           .date(LocalDate.of(2024, Month.FEBRUARY, 15))
                           .eventId(1L)
                           .availableAttendeesCount(100)
                           .description("description")
                           .category(Category.CONFERENCE)
                           .build();
        List<Ticket> expected = List.of(Ticket.builder()
                                              .ticketId(1L)
                                              .attendanceCount(10)
                                              .event(event)
                                              .user(user)
                                              .build());

        // when
        when(this.securityContext.getAuthentication()).thenReturn(this.authentication);
        when(this.authentication.getPrincipal()).thenReturn(userDetails);
        when(this.userAdapter.findByName(anyString())).thenReturn(user);
        when(this.ticketProjector.getAllBooked(anyLong())).thenReturn(expected);


        // act
        List<Ticket> actual = reservationAdapter.getAllBooked();

        // assert and verify
        Assertions.assertEquals(expected, actual);

        verify(this.securityContext, times(1)).getAuthentication();
        verify(this.authentication, times(1)).getPrincipal();
        verify(this.ticketProjector, times(1)).getAllBooked(anyLong());
        verify(this.userAdapter, times(1)).findByName(anyString());
    }

    @Test
    void getAll() {
        // given
        ReservationFilter filter = ReservationFilter.builder()
                                                    .reservationDate(LocalDate.of(2024, Month.FEBRUARY, 15))
                                                    .build();
        User user = User.builder()
                        .userId(1L)
                        .name("name")
                        .email("email")
                        .password("password")
                        .build();
        Event event = Event.builder()
                           .date(LocalDate.of(2024, Month.FEBRUARY, 15))
                           .eventId(1L)
                           .availableAttendeesCount(100)
                           .description("description")
                           .category(Category.CONFERENCE)
                           .build();
        List<Ticket> expected = List.of(Ticket.builder()
                                              .ticketId(1L)
                                              .attendanceCount(10)
                                              .event(event)
                                              .user(user)
                                              .build());

        // when
        when(this.ticketProjector.findAll(any(ReservationFilter.class))).thenReturn(expected);

        // act
        List<Ticket> actual = reservationAdapter.getAll(filter);

        // assert and verify
        Assertions.assertEquals(expected, actual);
        verify(this.ticketProjector, times(1)).findAll(any(ReservationFilter.class));
    }
}
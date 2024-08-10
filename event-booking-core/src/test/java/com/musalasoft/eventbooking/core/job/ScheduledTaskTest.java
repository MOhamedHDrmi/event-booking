package com.musalasoft.eventbooking.core.job;

import com.musalasoft.eventbooking.core.adapter.TicketReservationAdapter;
import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.domain.Ticket;
import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.domain.enums.Category;
import com.musalasoft.eventbooking.core.job.notification.NotificationSender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ScheduledTaskTest {

    @Mock
    TicketReservationAdapter reservationAdapter;
    @Mock
    List<NotificationSender> notificationSenders;
    @InjectMocks
    ScheduledTask scheduledTask;

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
    void sendUpcomingEventNotifications() {
        // given
        User user = User.builder()
                        .name("name")
                        .email("email")
                        .password("password")
                        .userId(1L)
                        .build();
        Event event = Event.builder()
                           .eventId(1L)
                           .name("name")
                           .date(LocalDate.of(2024, Month.FEBRUARY, 14))
                           .category(Category.CONCERT)
                           .description("description")
                           .availableAttendeesCount(100)
                           .build();
        Ticket ticket = Ticket.builder()
                              .ticketId(1L)
                              .user(user)
                              .event(event)
                              .attendanceCount(100)
                              .build();

        // when
        when(this.reservationAdapter.getAll(any())).thenReturn(List.of(ticket));

        // act
        this.scheduledTask.sendUpcomingEventNotifications();

        // assert
        verify(this.reservationAdapter, times(1)).getAll(any());
    }
}
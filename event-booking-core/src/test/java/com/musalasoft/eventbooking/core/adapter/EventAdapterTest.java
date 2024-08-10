package com.musalasoft.eventbooking.core.adapter;

import com.musalasoft.eventbooking.core.api.command.CreateEventCommand;
import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.domain.enums.Category;
import com.musalasoft.eventbooking.core.mapper.EventDomainMapper;
import com.musalasoft.eventbooking.core.portout.EventProjector;
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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EventAdapterTest {

    @Mock
    EventProjector eventProjector;
    @Mock
    EventDomainMapper domainMapper;
    @InjectMocks
    EventAdapter eventAdapter;

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
    void createEvent() {
        // given
        CreateEventCommand command = CreateEventCommand.builder()
                                                       .name("name")
                                                       .date(LocalDate.of(2024, Month.FEBRUARY, 14))
                                                       .category(Category.CONCERT)
                                                       .description("description")
                                                       .availableAttendeesCount(100)
                                                       .build();
        Event expected = Event.builder()
                              .name("name")
                              .date(LocalDate.of(2024, Month.FEBRUARY, 14))
                              .category(Category.CONCERT)
                              .description("description")
                              .availableAttendeesCount(100)
                              .build();

        // when
        when(this.domainMapper.toEvent(any())).thenReturn(Event.builder().build());
        when(this.domainMapper.toCreateSqlEventCommand(any())).thenReturn(null);
        when(this.eventProjector.createEvent(any())).thenReturn(expected);

        // act
        Event actual = this.eventAdapter.createEvent(command);

        // assert and verify
        Assertions.assertEquals(expected, actual);

        verify(this.domainMapper, times(1)).toCreateSqlEventCommand(any());
        verify(this.domainMapper, times(1)).toEvent(any());
        verify(this.eventProjector, times(1)).createEvent(any());
    }

    @Test
    void findAll() {
        // given

        // when
        when(eventProjector.findAll(any())).thenReturn(List.of(Event.builder().build()));

        // act
        List<Event> actual = eventAdapter.findAll(null);

        // assert
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(List.of(Event.builder().build()),  actual);
    }

    @Test
    void findById() {
        // given

        // when
        when(eventProjector.findById(anyLong())).thenReturn(Event.builder().build());

        // act
        Event actual = eventAdapter.findById(1L);

        // assert
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(Event.builder().build(), actual);
    }
}
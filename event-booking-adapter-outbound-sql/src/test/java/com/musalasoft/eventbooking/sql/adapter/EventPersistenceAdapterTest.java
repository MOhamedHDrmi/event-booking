package com.musalasoft.eventbooking.sql.adapter;

import com.musalasoft.eventbooking.core.api.filter.EventFilter;
import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.domain.enums.Category;
import com.musalasoft.eventbooking.core.exception.ResourceNotFoundException;
import com.musalasoft.eventbooking.core.sql.command.CreateEventSqlCommand;
import com.musalasoft.eventbooking.sql.entity.EventEntity;
import com.musalasoft.eventbooking.sql.mapper.EventSqlMapper;
import com.musalasoft.eventbooking.sql.repository.EventRepository;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EventPersistenceAdapterTest {

    @Mock
    EventRepository eventRepository;
    @Mock
    EventSqlMapper mapper;
    @InjectMocks
    EventPersistenceAdapter eventPersistenceAdapter;

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
    void createEvent() {
        // given
        Event expected = Event.builder()
                           .eventId(1L).name("name")
                           .date(LocalDate.of(2024, Month.FEBRUARY, 14))
                           .availableAttendeesCount(100)
                           .category(Category.GAME)
                           .description("description")
                           .build();
        CreateEventSqlCommand command = CreateEventSqlCommand.builder()
                                                             .date(LocalDate.of(2024, Month.FEBRUARY, 14))
                                                             .availableAttendeesCount(100)
                                                             .category(Category.GAME)
                                                             .description("description")
                                                             .build();
        // when
        when(this.mapper.toEventEntity(any())).thenReturn(new EventEntity());
        when(this.eventRepository.save(any())).thenReturn(new EventEntity());
        when(this.mapper.toEvent(any())).thenReturn(expected);

        // act
        Event actual = this.eventPersistenceAdapter.createEvent(command);

        // assert and verify
        Assertions.assertEquals(expected, actual);

        verify(this.eventRepository, times(1)).save(any());
        verify(this.mapper, times(1)).toEvent(any());
        verify(this.mapper, times(1)).toEventEntity(any());
    }

    @Test
    void findAll_WithValidFilter() {
        // given
        Event event = Event.builder()
                              .eventId(1L).name("name")
                              .date(LocalDate.of(2024, Month.FEBRUARY, 14))
                              .availableAttendeesCount(100)
                              .category(Category.GAME)
                              .description("description")
                              .build();
        EventFilter filter = EventFilter.builder()
                                        .startDate(LocalDate.of(2024, Month.FEBRUARY, 14))
                                        .endDate(LocalDate.of(2024, Month.FEBRUARY, 14))
                                        .category(Category.GAME)
                                        .name("name")
                                        .build();

        // when
        when(this.eventRepository.findAll(any(Predicate.class))).thenReturn(List.of(new EventEntity()));
        when(this.mapper.toEvent(any())).thenReturn(event);

        // act
        List<Event> result = this.eventPersistenceAdapter.findAll(filter);

        // assert and verify
        Assertions.assertFalse(result.isEmpty());

        verify(this.mapper, times(1)).toEvent(any());
        verify(this.eventRepository, times(1)).findAll(any(Predicate.class));

    }

    @Test
    void findAll_WithInvalidFilter() {
        // given
        Event event = Event.builder()
                           .eventId(1L).name("name")
                           .date(LocalDate.of(2024, Month.FEBRUARY, 14))
                           .availableAttendeesCount(100)
                           .category(Category.GAME)
                           .description("description")
                           .build();

        // when
        when(this.eventRepository.findAll()).thenReturn(List.of(new EventEntity()));
        when(this.mapper.toEvent(any())).thenReturn(event);

        // act
        List<Event> result = this.eventPersistenceAdapter.findAll(null);

        // assert and verify
        Assertions.assertFalse(result.isEmpty());

        verify(this.mapper, times(1)).toEvent(any());
        verify(this.eventRepository, times(1)).findAll();

    }

    @Test
    void findByIdWithValidId() {
        // given
        Event event = Event.builder()
                           .eventId(1L)
                           .name("name")
                           .date(LocalDate.of(2024, Month.FEBRUARY, 14))
                           .availableAttendeesCount(100)
                           .category(Category.GAME)
                           .description("description")
                           .build();

        // when
        when(this.mapper.toEvent(any())).thenReturn(event);
        when(this.eventRepository.findById(any())).thenReturn(Optional.of(new EventEntity()));

        // act
        Event result = this.eventPersistenceAdapter.findById(1L);

        // assert and verify
        Assertions.assertEquals(event, result);

        verify(this.eventRepository,times(1)).findById(any());
        verify(this.mapper, times(1)).toEvent(any());
    }

    @Test
    void findByIdWithInvalidId() {
        // given
        Long eventId = 1L;

        // when
        when(this.eventRepository.findById(any())).thenReturn(Optional.empty());

        // act

        // assert and verify
        Assertions.assertThrows(ResourceNotFoundException.class,
                                () -> this.eventPersistenceAdapter.findById(eventId),
                                "Can't find Event with Id: " + eventId);

        verify(this.eventRepository,times(1)).findById(any());
    }
}
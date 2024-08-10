package com.musalasoft.eventbooking.core.mapper;

import com.musalasoft.eventbooking.core.api.command.CreateEventCommand;
import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.domain.enums.Category;
import com.musalasoft.eventbooking.core.sql.command.CreateEventSqlCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.Month;

class EventDomainMapperTest {

    private EventDomainMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = Mappers.getMapper(EventDomainMapper.class);
    }

    @Test
    void toEvent_WithValidInput() {
        // when
        CreateEventCommand command = CreateEventCommand.builder()
                                                       .name("name")
                                                       .availableAttendeesCount(100)
                                                       .description("description")
                                                       .category(Category.CONFERENCE)
                                                       .date(LocalDate.of(2024, Month.FEBRUARY, 10))
                                                       .build();
        Event expected = Event.builder()
                              .name("name")
                              .availableAttendeesCount(100)
                              .description("description")
                              .category(Category.CONFERENCE)
                              .date(LocalDate.of(2024, Month.FEBRUARY, 10))
                              .build();

        // act
        Event actual = this.mapper.toEvent(command);

        // assert and verify
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toEvent_WithNullInput() {
        Assertions.assertNull(this.mapper.toEvent(null));
    }

    @Test
    void toCreateSqlEventCommand_WithValidInput() {
        // given
        Event event = Event.builder()
                              .name("name")
                              .availableAttendeesCount(100)
                              .description("description")
                              .category(Category.CONFERENCE)
                              .date(LocalDate.of(2024, Month.FEBRUARY, 10))
                              .build();
        CreateEventSqlCommand expected = CreateEventSqlCommand.builder()
                                                              .name("name")
                                                              .availableAttendeesCount(100)
                                                              .description("description")
                                                              .category(Category.CONFERENCE)
                                                              .date(LocalDate.of(2024, Month.FEBRUARY, 10))
                                                              .build();

        // act
        CreateEventSqlCommand actual = this.mapper.toCreateSqlEventCommand(event);

        // assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toCreateSqlEventCommand_WithNullInput() {
        Assertions.assertNull(this.mapper.toCreateSqlEventCommand(null));
    }
}
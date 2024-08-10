package com.musalasoft.eventbooking.sql.mapper;

import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.domain.enums.Category;
import com.musalasoft.eventbooking.core.sql.command.CreateEventSqlCommand;
import com.musalasoft.eventbooking.sql.entity.EventEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.Month;

class EventSqlMapperTest {

    private EventSqlMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = Mappers.getMapper(EventSqlMapper.class);
    }

    @Test
    void toEventEntity_WithValidInput() {
        CreateEventSqlCommand command = CreateEventSqlCommand.builder()
                                                             .date(LocalDate.of(2024, Month.FEBRUARY, 10))
                                                             .name("name")
                                                             .availableAttendeesCount(1000)
                                                             .description("description")
                                                             .category(Category.GAME)
                                                             .build();

        EventEntity expected = new EventEntity();
        expected.setDate(LocalDate.of(2024, Month.FEBRUARY, 10));
        expected.setName("name");
        expected.setAvailableAttendeesCount(1000);
        expected.setDescription("description");
        expected.setCategory(Category.GAME);

        EventEntity actual = this.mapper.toEventEntity(command);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void toEventEntity_WithNullInput() {
        Assertions.assertNull(this.mapper.toEventEntity(null));
    }

    @Test
    void toEvent_WithValidInput() {
        // given
        EventEntity eventEntity = new EventEntity();
        eventEntity.setDate(LocalDate.of(2024, Month.FEBRUARY, 10));
        eventEntity.setName("name");
        eventEntity.setAvailableAttendeesCount(1000);
        eventEntity.setDescription("description");
        eventEntity.setCategory(Category.GAME);

        Event expected = Event.builder()
                              .date(LocalDate.of(2024, Month.FEBRUARY, 10))
                              .name("name")
                              .availableAttendeesCount(1000)
                              .description("description")
                              .category(Category.GAME)
                              .build();

        Event actual = this.mapper.toEvent(eventEntity);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toEvent_WithNullInput() {
        Assertions.assertNull(this.mapper.toEvent(null));
    }
}
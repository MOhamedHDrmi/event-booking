package com.musalasoft.eventbooking.rest.mapper;

import com.musalasoft.eventbooking.core.api.command.CreateEventCommand;
import com.musalasoft.eventbooking.core.api.filter.EventFilter;
import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.domain.enums.Category;
import com.musalasoft.eventbooking.rest.model.CreateEvent201Response;
import com.musalasoft.eventbooking.rest.model.EventRequestDTO;
import com.musalasoft.eventbooking.rest.model.EventResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

class EventMapperTest {

    private EventMapper eventMapper;

    @BeforeEach
    void setUp() {
        this.eventMapper = Mappers.getMapper(EventMapper.class);
    }

    @Test
    void toCreateEventCommand_WithValidInput() {
        CreateEventCommand expected = CreateEventCommand.builder()
                                                        .date(LocalDate.of(2024, 2, 10))
                                                        .availableAttendeesCount(100)
                                                        .description("description")
                                                        .category(Category.CONCERT)
                                                        .name("event-name")
                                                        .build();

        EventRequestDTO requestDTO = new EventRequestDTO();
        requestDTO.setDate(LocalDate.of(2024, 2, 10));
        requestDTO.setAvailableAttendeesCount(100);
        requestDTO.setDescription("description");
        requestDTO.setCategory(com.musalasoft.eventbooking.rest.model.Category.CONCERT);
        requestDTO.setName("event-name");


        CreateEventCommand actual = this.eventMapper.toCreateEventCommand(requestDTO);


        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toCreateEventCommand_WithNullInput() {
        Assertions.assertNull(this.eventMapper.toCreateEventCommand(null));
    }

    @Test
    void toCreateEvent201Response_WithValidInput() {
        CreateEvent201Response expected = new CreateEvent201Response();
        expected.setEventId(10L);

        CreateEvent201Response actual = this.eventMapper.toCreateEvent201Response(Event.builder()
                                                                                       .eventId(10L)
                                                                                       .build());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toEventResponseDTO_WithValidInput() {
        EventResponseDTO expected = new EventResponseDTO();
        expected.setCategory(com.musalasoft.eventbooking.rest.model.Category.CONCERT);
        expected.setId(10L);
        expected.setDescription("description");
        expected.setName("name");
        expected.setDate(LocalDate.of(2024, 2, 10));
        expected.setAvailableAttendeesCount(100);

        Event event = Event.builder()
                           .category(Category.CONCERT)
                           .eventId(10L)
                           .description("description")
                           .name("name")
                           .date(LocalDate.of(2024, 2, 10))
                           .availableAttendeesCount(100)
                           .build();


        EventResponseDTO actual = this.eventMapper.toEventResponseDTO(event);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toEventResponseDTO_WithNullInput() {
        Assertions.assertNull(this.eventMapper.toEventResponseDTO(null));
    }

    @Test
    void toEventFilter_WithValidInput() {
        EventFilter expected = EventFilter.builder()
                                          .name("name")
                                          .endDate(LocalDate.of(2024, 2, 10))
                                          .startDate(LocalDate.of(2024, 2, 10))
                                          .category(Category.GAME)
                                          .build();

        EventFilter actual = this.eventMapper.toEventFilter("name", LocalDate.of(2024, 2, 10), LocalDate.of(2024, 2, 10), "Game");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toEventFilter_WithNullInput() {
        Assertions.assertNull(this.eventMapper.toEventFilter(null, null, null, null));
    }

    @Test
    void toCreateEvent201Response_WithNullInput() {
        Assertions.assertNull(this.eventMapper.toCreateEvent201Response(null));
    }

    @Test
    void testToCategory_WithNullInput() {
        Category result = eventMapper.toCategory(null);
        Assertions.assertNull(result);
    }

    @Test
    void testToCategory_WithValidInput() {
        Category result = eventMapper.toCategory(com.musalasoft.eventbooking.rest.model.Category.CONFERENCE);
        Assertions.assertEquals(Category.CONFERENCE, result);
    }
}
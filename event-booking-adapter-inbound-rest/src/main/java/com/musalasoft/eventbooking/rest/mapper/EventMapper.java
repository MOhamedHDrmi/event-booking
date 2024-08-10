package com.musalasoft.eventbooking.rest.mapper;

import com.musalasoft.eventbooking.core.api.command.CreateEventCommand;
import com.musalasoft.eventbooking.core.api.filter.EventFilter;
import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.domain.enums.Category;
import com.musalasoft.eventbooking.rest.model.CreateEvent201Response;
import com.musalasoft.eventbooking.rest.model.EventRequestDTO;
import com.musalasoft.eventbooking.rest.model.EventResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface EventMapper {

    CreateEventCommand toCreateEventCommand(EventRequestDTO requestDTO);

    CreateEvent201Response toCreateEvent201Response(Event createdEvent);

    @Mapping(target = "category", expression = "java(com.musalasoft.eventbooking.core.domain.enums.Category.fromValue(category))")
    EventFilter toEventFilter(String name, LocalDate startDate, LocalDate endDate, String category);

    @Mapping(target = "id", source = "eventId")
    EventResponseDTO toEventResponseDTO(Event event);

    default Category toCategory(com.musalasoft.eventbooking.rest.model.Category category) {
        return category == null ? null : Category.fromValue(category.getValue());
    }
}

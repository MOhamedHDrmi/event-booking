package com.musalasoft.eventbooking.core.mapper;

import com.musalasoft.eventbooking.core.api.command.CreateEventCommand;
import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.sql.command.CreateEventSqlCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventDomainMapper {

    Event toEvent(CreateEventCommand command);

    CreateEventSqlCommand toCreateSqlEventCommand(Event event);

}

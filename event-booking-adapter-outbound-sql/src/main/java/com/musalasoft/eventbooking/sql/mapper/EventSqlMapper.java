package com.musalasoft.eventbooking.sql.mapper;

import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.sql.command.CreateEventSqlCommand;
import com.musalasoft.eventbooking.sql.entity.EventEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventSqlMapper {

    EventEntity toEventEntity(CreateEventSqlCommand command);

    Event toEvent(EventEntity eventEntity);

}

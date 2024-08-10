package com.musalasoft.eventbooking.core.portout;

import com.musalasoft.eventbooking.core.api.filter.EventFilter;
import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.sql.command.CreateEventSqlCommand;

import java.util.List;

public interface EventProjector {

    Event createEvent(CreateEventSqlCommand command);

    List<Event> findAll(EventFilter filter);

    Event findById(Long eventId);
}

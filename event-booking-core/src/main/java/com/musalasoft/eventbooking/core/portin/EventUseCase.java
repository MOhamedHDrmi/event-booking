package com.musalasoft.eventbooking.core.portin;

import com.musalasoft.eventbooking.core.api.command.CreateEventCommand;
import com.musalasoft.eventbooking.core.api.filter.EventFilter;
import com.musalasoft.eventbooking.core.domain.Event;

import java.util.List;

public interface EventUseCase {

    Event createEvent(CreateEventCommand command);

    List<Event> findAll(EventFilter filter);

    Event findById(Long eventId);
}

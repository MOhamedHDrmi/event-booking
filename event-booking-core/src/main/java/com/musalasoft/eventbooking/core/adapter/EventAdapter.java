package com.musalasoft.eventbooking.core.adapter;

import com.musalasoft.eventbooking.core.api.command.CreateEventCommand;
import com.musalasoft.eventbooking.core.api.filter.EventFilter;
import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.mapper.EventDomainMapper;
import com.musalasoft.eventbooking.core.portin.EventUseCase;
import com.musalasoft.eventbooking.core.portout.EventProjector;
import com.musalasoft.eventbooking.core.sql.command.CreateEventSqlCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EventAdapter implements EventUseCase {

    private final EventProjector eventProjector;
    private final EventDomainMapper domainMapper;

    EventAdapter(EventProjector eventProjector, EventDomainMapper domainMapper) {
        this.eventProjector = eventProjector;
        this.domainMapper = domainMapper;
    }

    @Override
    public Event createEvent(CreateEventCommand command) {
        Event event = this.domainMapper.toEvent(command);

        CreateEventSqlCommand sqlEventCommand = this.domainMapper.toCreateSqlEventCommand(event);

        log.info("Create new event: {}", sqlEventCommand);

        return this.eventProjector.createEvent(sqlEventCommand);
    }

    @Override
    public List<Event> findAll(EventFilter filter) {
        log.info("Fetch all events matching filter: {}", filter);

        return this.eventProjector.findAll(filter);
    }

    @Override
    public Event findById(Long eventId) {
        return this.eventProjector.findById(eventId);
    }
}

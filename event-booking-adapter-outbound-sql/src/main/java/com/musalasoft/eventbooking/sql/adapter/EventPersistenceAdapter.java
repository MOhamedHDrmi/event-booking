package com.musalasoft.eventbooking.sql.adapter;

import com.musalasoft.eventbooking.core.api.filter.EventFilter;
import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.exception.ResourceNotFoundException;
import com.musalasoft.eventbooking.core.portout.EventProjector;
import com.musalasoft.eventbooking.core.sql.command.CreateEventSqlCommand;
import com.musalasoft.eventbooking.sql.entity.EventEntity;
import com.musalasoft.eventbooking.sql.entity.QEventEntity;
import com.musalasoft.eventbooking.sql.mapper.EventSqlMapper;
import com.musalasoft.eventbooking.sql.repository.EventRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventPersistenceAdapter implements EventProjector {

    private final EventRepository eventRepository;
    private final EventSqlMapper mapper;

    EventPersistenceAdapter(EventRepository eventRepository, EventSqlMapper mapper) {
        this.eventRepository =eventRepository;
        this.mapper = mapper;
    }

    @Override
    public Event createEvent(CreateEventSqlCommand command) {
        EventEntity eventEntity = this.mapper.toEventEntity(command);

        eventEntity = this.eventRepository.save(eventEntity);

        return this.mapper.toEvent(eventEntity);
    }

    @Override
    public List<Event> findAll(EventFilter filter) {
        List<EventEntity> eventEntities;
        Predicate predicate = this.buildPredicate(filter);

        if (predicate != null) {
            eventEntities = this.eventRepository.findAll(predicate);
        } else {
            eventEntities = this.eventRepository.findAll();
        }

        return eventEntities.stream()
                            .map(this.mapper::toEvent)
                            .collect(Collectors.toList());
    }

    @Override
    public Event findById(Long eventId) {
        Optional<EventEntity> eventEntity = this.eventRepository.findById(eventId);
        return eventEntity.map(mapper::toEvent)
                          .orElseThrow(() -> new ResourceNotFoundException(String.format("Can't find Event with Id: %s", eventId)));
    }

    private Predicate buildPredicate(EventFilter filter) {
        if (filter == null)
            return null;

        QEventEntity event = QEventEntity.eventEntity;
        BooleanBuilder predicateBuilder = new BooleanBuilder();

        if (filter.getName() != null) {
            predicateBuilder.and(event.name.eq(filter.getName()));
        }

        if (filter.getStartDate() != null) {
            predicateBuilder.and(event.date.goe(filter.getStartDate()));
        }

        if (filter.getEndDate() != null) {
            predicateBuilder.and(event.date.loe(filter.getEndDate()));
        }

        if (filter.getCategory() != null) {
            predicateBuilder.and(event.category.eq(filter.getCategory()));
        }

        return predicateBuilder.getValue();
    }
}

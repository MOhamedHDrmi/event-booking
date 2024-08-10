package com.musalasoft.eventbooking.sql.adapter;

import com.musalasoft.eventbooking.core.api.filter.ReservationFilter;
import com.musalasoft.eventbooking.core.domain.Ticket;
import com.musalasoft.eventbooking.core.exception.InvalidParameterValueException;
import com.musalasoft.eventbooking.core.portout.TicketProjector;
import com.musalasoft.eventbooking.core.sql.command.CancelTicketSqlCommand;
import com.musalasoft.eventbooking.core.sql.command.ReserveTicketSqlCommand;
import com.musalasoft.eventbooking.sql.entity.EventEntity;
import com.musalasoft.eventbooking.sql.entity.QTicketEntity;
import com.musalasoft.eventbooking.sql.entity.TicketEntity;
import com.musalasoft.eventbooking.sql.mapper.TicketReservationSqlMapper;
import com.musalasoft.eventbooking.sql.repository.EventRepository;
import com.musalasoft.eventbooking.sql.repository.TicketRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketPersistenceAdapter implements TicketProjector {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final TicketReservationSqlMapper mapper;

    public TicketPersistenceAdapter(TicketRepository ticketRepository, EventRepository eventRepository, TicketReservationSqlMapper mapper) {
        this.ticketRepository
            = ticketRepository;
        this.eventRepository = eventRepository;
        this.mapper = mapper;
    }

    @Override
    public void reserveEventTicket(ReserveTicketSqlCommand reserveTicketSqlCommand) {
        TicketEntity ticketEntity = this.mapper.toReservationEntity(reserveTicketSqlCommand);

        int currentAttendance = ticketRepository.findByEvent_EventId(reserveTicketSqlCommand.getEventId())
                                                .stream()
                                                .mapToInt(TicketEntity::getAttendanceCount)
                                                .sum();

        Optional<EventEntity> event = this.eventRepository.findById(reserveTicketSqlCommand.getEventId());

        int availableAttendance = event.map(EventEntity::getAvailableAttendeesCount).orElse(0);

        boolean isMaxAttendanceReached = currentAttendance + ticketEntity.getAttendanceCount() > availableAttendance;

        if (isMaxAttendanceReached) {
            throw new InvalidParameterValueException("Maximum attendance reached for this event.");
        }

        this.ticketRepository.save(ticketEntity);
    }

    @Override
    @Transactional
    public void deleteEventReservationTicket(CancelTicketSqlCommand command) {
        this.ticketRepository.deleteById(command.getTicketId());
    }

    @Override
    public List<Ticket> getAllBooked(Long userId) {
        return this.ticketRepository.findByUser_UserId(userId).stream()
                                    .map(this.mapper::toReservation)
                                    .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> findAll(ReservationFilter filter) {
        List<TicketEntity> reservationEntities;
        Predicate predicate = this.buildPredicate(filter);

        if (predicate != null) {
            reservationEntities = this.ticketRepository.findAll(predicate);
        } else {
            reservationEntities = this.ticketRepository.findAll();
        }

        return reservationEntities.stream()
                                  .map(mapper::toReservation)
                                  .collect(Collectors.toList());
    }

    private Predicate buildPredicate(ReservationFilter filter) {
        if (filter == null)
            return null;

        QTicketEntity reservation = QTicketEntity.ticketEntity;
        BooleanBuilder predicateBuilder = new BooleanBuilder();

        if (filter.getReservationDate() != null) {
            predicateBuilder.and(reservation.event.date.eq(filter.getReservationDate()));
        }

        return predicateBuilder.getValue();
    }
}

package com.musalasoft.eventbooking.sql.mapper;

import com.musalasoft.eventbooking.core.domain.Ticket;
import com.musalasoft.eventbooking.core.sql.command.ReserveTicketSqlCommand;
import com.musalasoft.eventbooking.sql.entity.EventEntity;
import com.musalasoft.eventbooking.sql.entity.TicketEntity;
import com.musalasoft.eventbooking.sql.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketReservationSqlMapper {

    Ticket toReservation(TicketEntity ticketEntity);

    @Mapping(target = "user", expression = "java(this.toUserEntity(command.getUserId()))")
    @Mapping(target = "event", expression = "java(this.toEventEntity(command.getEventId()))")
    @Mapping(target = "attendanceCount", source = "command.attendeesCount")
    TicketEntity toReservationEntity(ReserveTicketSqlCommand command);

    default UserEntity toUserEntity(Long userId) {
        UserEntity user = new UserEntity();
        user.setUserId(userId);

        return user;
    }

    default EventEntity toEventEntity(Long eventId) {
        EventEntity event = new EventEntity();
        event.setEventId(eventId);

        return event;
    }
}

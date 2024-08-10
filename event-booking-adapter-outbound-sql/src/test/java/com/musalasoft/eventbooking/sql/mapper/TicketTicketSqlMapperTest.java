package com.musalasoft.eventbooking.sql.mapper;

import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.domain.Ticket;
import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.sql.command.ReserveTicketSqlCommand;
import com.musalasoft.eventbooking.sql.entity.EventEntity;
import com.musalasoft.eventbooking.sql.entity.TicketEntity;
import com.musalasoft.eventbooking.sql.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class TicketTicketSqlMapperTest {

    private TicketReservationSqlMapper ticketReservationSqlMapper;

    @BeforeEach
    void setUP() {
        this.ticketReservationSqlMapper = Mappers.getMapper(TicketReservationSqlMapper.class);
    }

    @Test
    void toReservation_WithValidInput() {
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setUser(new UserEntity());
        ticketEntity.setEvent(new EventEntity());
        ticketEntity.setAttendanceCount(100);

        Ticket expected = Ticket.builder()
                                .attendanceCount(100)
                                .user(User.builder().build())
                                .event(Event.builder().build())
                                .build();

        Ticket actual = this.ticketReservationSqlMapper.toReservation(ticketEntity);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toReservation_WithNullInput() {
        Assertions.assertNull(this.ticketReservationSqlMapper.toReservation(null));
    }

    @Test
    void toReservationEntity_WithValidInput() {
        // given
        ReserveTicketSqlCommand command = ReserveTicketSqlCommand.builder()
                                                                 .attendeesCount(100)
                                                                 .userId(1L)
                                                                 .eventId(1L)
                                                                 .build();
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1L);

        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventId(1L);

        TicketEntity expected = new TicketEntity();
        expected.setUser(userEntity);
        expected.setEvent(eventEntity);
        expected.setAttendanceCount(100);

        TicketEntity actual = this.ticketReservationSqlMapper.toReservationEntity(command);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toReservationEntity_WithNullInput() {
        Assertions.assertNull(this.ticketReservationSqlMapper.toReservationEntity(null));
    }

    @Test
    void testToUserEntity() {
        UserEntity result = this.ticketReservationSqlMapper.toUserEntity(1L);

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1L);

        Assertions.assertEquals(userEntity, result);
    }

    @Test
    void testToEventEntity() {
        EventEntity result = this.ticketReservationSqlMapper.toEventEntity(1L);

        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventId(1L);

        Assertions.assertEquals(eventEntity, result);
    }
}
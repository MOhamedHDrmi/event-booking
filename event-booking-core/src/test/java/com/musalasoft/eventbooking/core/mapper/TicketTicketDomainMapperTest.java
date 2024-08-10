package com.musalasoft.eventbooking.core.mapper;

import com.musalasoft.eventbooking.core.api.command.CancelTicketCommand;
import com.musalasoft.eventbooking.core.api.command.ReserveEventTicketCommand;
import com.musalasoft.eventbooking.core.sql.command.CancelTicketSqlCommand;
import com.musalasoft.eventbooking.core.sql.command.ReserveTicketSqlCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class TicketTicketDomainMapperTest {

    private TicketReservationDomainMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = Mappers.getMapper(TicketReservationDomainMapper.class);
    }

    @Test
    void toReserveSqlEventCommand_WithValidInput() {
        // given
        Long userId= 1L;
        ReserveEventTicketCommand command = ReserveEventTicketCommand.builder()
                                                                     .attendeesCount(100)
                                                                     .eventId(1L)
                                                                     .build();
        ReserveTicketSqlCommand expected = ReserveTicketSqlCommand.builder()
                                                                  .attendeesCount(100)
                                                                  .eventId(1L)
                                                                  .userId(userId)
                                                                  .build();

        // act
        ReserveTicketSqlCommand actual = this.mapper.toReserveSqlEventCommand(userId, command);

        // assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toReserveSqlEventCommand_WithNullInput() {
        Assertions.assertNull(this.mapper.toReserveSqlEventCommand(null, null));
    }

    @Test
    void toCancelSqlReservationCommand_WithValidInput() {
        // given
        CancelTicketCommand command = CancelTicketCommand.builder()
                                                         .ticketId(1L)
                                                         .build();
        CancelTicketSqlCommand expected = CancelTicketSqlCommand.builder()
                                                                .ticketId(1L)
                                                                .build();

        // act
        CancelTicketSqlCommand actual = this.mapper.toCancelSqlReservationCommand(command);

        // assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toCancelSqlReservationCommand_WithNullInput() {
        Assertions.assertNull(this.mapper.toCancelSqlReservationCommand(null));
    }
}
package com.musalasoft.eventbooking.core.mapper;

import com.musalasoft.eventbooking.core.api.command.CancelTicketCommand;
import com.musalasoft.eventbooking.core.api.command.ReserveEventTicketCommand;
import com.musalasoft.eventbooking.core.sql.command.CancelTicketSqlCommand;
import com.musalasoft.eventbooking.core.sql.command.ReserveTicketSqlCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketReservationDomainMapper {

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "eventId", source = "command.eventId")
    @Mapping(target = "attendeesCount", source = "command.attendeesCount")
    ReserveTicketSqlCommand toReserveSqlEventCommand(Long userId, ReserveEventTicketCommand command);

    CancelTicketSqlCommand toCancelSqlReservationCommand(CancelTicketCommand command);
}

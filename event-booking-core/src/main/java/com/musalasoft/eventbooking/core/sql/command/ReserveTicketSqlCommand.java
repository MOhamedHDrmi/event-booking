package com.musalasoft.eventbooking.core.sql.command;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReserveTicketSqlCommand {

    Long userId;
    Long eventId;
    int attendeesCount;
}

package com.musalasoft.eventbooking.core.api.command;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReserveEventTicketCommand {

    Long eventId;
    int attendeesCount;
}

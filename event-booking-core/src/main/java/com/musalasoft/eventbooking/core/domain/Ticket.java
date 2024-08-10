package com.musalasoft.eventbooking.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Ticket {

    Long ticketId;
    int attendanceCount;
    User user;
    Event event;
}

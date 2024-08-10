package com.musalasoft.eventbooking.core.api.filter;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReservationFilter {
    LocalDate reservationDate;
}

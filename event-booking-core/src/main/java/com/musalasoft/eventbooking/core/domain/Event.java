package com.musalasoft.eventbooking.core.domain;

import com.musalasoft.eventbooking.core.domain.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Event {

    Long eventId;
    String name;
    LocalDate date;
    Integer availableAttendeesCount;
    String description;
    Category category;
}

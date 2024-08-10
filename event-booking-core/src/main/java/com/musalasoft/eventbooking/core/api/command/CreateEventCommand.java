package com.musalasoft.eventbooking.core.api.command;

import com.musalasoft.eventbooking.core.domain.enums.Category;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class CreateEventCommand {

    String name;
    LocalDate date;
    Integer availableAttendeesCount;
    String description;
    Category category;
}

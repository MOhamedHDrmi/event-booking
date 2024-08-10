package com.musalasoft.eventbooking.core.sql.command;

import com.musalasoft.eventbooking.core.domain.enums.Category;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class CreateEventSqlCommand {

    String name;
    LocalDate date;
    Integer availableAttendeesCount;
    String description;
    Category category;
}

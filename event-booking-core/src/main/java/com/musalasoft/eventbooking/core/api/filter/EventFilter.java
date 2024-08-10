package com.musalasoft.eventbooking.core.api.filter;

import com.musalasoft.eventbooking.core.domain.enums.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EventFilter {

    String name;
    LocalDate startDate;
    LocalDate endDate;
    Category category;
}

package com.musalasoft.eventbooking.sql.entity;

import com.musalasoft.eventbooking.core.domain.enums.Category;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity(name = "events")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false, unique = true)
    Long eventId;
    @Size(min = 1, max = 100)
    String name;
    @DateTimeFormat
    LocalDate date;
    @Size(min=1, max = 1000)
    @Column(name = "available_attendees_count")
    Integer availableAttendeesCount;
    @Size(max = 500)
    String description;
    @Enumerated(EnumType.STRING)
    Category category;
}

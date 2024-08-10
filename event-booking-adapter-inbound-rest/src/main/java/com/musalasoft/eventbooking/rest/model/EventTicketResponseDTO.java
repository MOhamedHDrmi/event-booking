package com.musalasoft.eventbooking.rest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * EventTicketResponseDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-10T15:36:30.869585+03:00[Africa/Cairo]")
public class EventTicketResponseDTO {

  @JsonProperty("ticketId")
  private Long ticketId;

  @JsonProperty("eventId")
  private Long eventId;

  @JsonProperty("attendanceCount")
  private Integer attendanceCount;

  public EventTicketResponseDTO ticketId(Long ticketId) {
    this.ticketId = ticketId;
    return this;
  }

  /**
   * Get ticketId
   * @return ticketId
  */
  
  @Schema(name = "ticketId", required = false)
  public Long getTicketId() {
    return ticketId;
  }

  public void setTicketId(Long ticketId) {
    this.ticketId = ticketId;
  }

  public EventTicketResponseDTO eventId(Long eventId) {
    this.eventId = eventId;
    return this;
  }

  /**
   * Get eventId
   * @return eventId
  */
  
  @Schema(name = "eventId", required = false)
  public Long getEventId() {
    return eventId;
  }

  public void setEventId(Long eventId) {
    this.eventId = eventId;
  }

  public EventTicketResponseDTO attendanceCount(Integer attendanceCount) {
    this.attendanceCount = attendanceCount;
    return this;
  }

  /**
   * Get attendanceCount
   * @return attendanceCount
  */
  
  @Schema(name = "attendanceCount", required = false)
  public Integer getAttendanceCount() {
    return attendanceCount;
  }

  public void setAttendanceCount(Integer attendanceCount) {
    this.attendanceCount = attendanceCount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventTicketResponseDTO eventTicketResponseDTO = (EventTicketResponseDTO) o;
    return Objects.equals(this.ticketId, eventTicketResponseDTO.ticketId) &&
        Objects.equals(this.eventId, eventTicketResponseDTO.eventId) &&
        Objects.equals(this.attendanceCount, eventTicketResponseDTO.attendanceCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ticketId, eventId, attendanceCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventTicketResponseDTO {\n");
    sb.append("    ticketId: ").append(toIndentedString(ticketId)).append("\n");
    sb.append("    eventId: ").append(toIndentedString(eventId)).append("\n");
    sb.append("    attendanceCount: ").append(toIndentedString(attendanceCount)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


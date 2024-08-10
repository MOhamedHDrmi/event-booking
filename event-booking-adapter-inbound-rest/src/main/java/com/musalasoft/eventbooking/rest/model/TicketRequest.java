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
 * TicketRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-10T15:36:30.869585+03:00[Africa/Cairo]")
public class TicketRequest {

  @JsonProperty("attendeesCount")
  private Integer attendeesCount;

  public TicketRequest attendeesCount(Integer attendeesCount) {
    this.attendeesCount = attendeesCount;
    return this;
  }

  /**
   * Get attendeesCount
   * minimum: 1
   * @return attendeesCount
  */
  @NotNull @Min(1) 
  @Schema(name = "attendeesCount", required = true)
  public Integer getAttendeesCount() {
    return attendeesCount;
  }

  public void setAttendeesCount(Integer attendeesCount) {
    this.attendeesCount = attendeesCount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketRequest ticketRequest = (TicketRequest) o;
    return Objects.equals(this.attendeesCount, ticketRequest.attendeesCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attendeesCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TicketRequest {\n");
    sb.append("    attendeesCount: ").append(toIndentedString(attendeesCount)).append("\n");
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


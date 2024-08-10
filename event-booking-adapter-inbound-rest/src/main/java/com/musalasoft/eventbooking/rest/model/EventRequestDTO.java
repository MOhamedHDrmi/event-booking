package com.musalasoft.eventbooking.rest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.musalasoft.eventbooking.rest.model.Category;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * EventRequestDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-10T15:36:30.869585+03:00[Africa/Cairo]")
public class EventRequestDTO {

  @JsonProperty("name")
  private String name;

  @JsonProperty("date")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate date;

  @JsonProperty("availableAttendeesCount")
  private Integer availableAttendeesCount;

  @JsonProperty("description")
  private String description;

  @JsonProperty("category")
  private Category category;

  public EventRequestDTO name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @NotNull @Size(max = 100) 
  @Schema(name = "name", required = true)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public EventRequestDTO date(LocalDate date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
  */
  @NotNull @Valid 
  @Schema(name = "date", required = true)
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public EventRequestDTO availableAttendeesCount(Integer availableAttendeesCount) {
    this.availableAttendeesCount = availableAttendeesCount;
    return this;
  }

  /**
   * Get availableAttendeesCount
   * minimum: 1
   * maximum: 1000
   * @return availableAttendeesCount
  */
  @NotNull @Min(1) @Max(1000) 
  @Schema(name = "availableAttendeesCount", required = true)
  public Integer getAvailableAttendeesCount() {
    return availableAttendeesCount;
  }

  public void setAvailableAttendeesCount(Integer availableAttendeesCount) {
    this.availableAttendeesCount = availableAttendeesCount;
  }

  public EventRequestDTO description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  @NotNull @Size(max = 500) 
  @Schema(name = "description", required = true)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public EventRequestDTO category(Category category) {
    this.category = category;
    return this;
  }

  /**
   * Get category
   * @return category
  */
  @NotNull @Valid 
  @Schema(name = "category", required = true)
  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventRequestDTO eventRequestDTO = (EventRequestDTO) o;
    return Objects.equals(this.name, eventRequestDTO.name) &&
        Objects.equals(this.date, eventRequestDTO.date) &&
        Objects.equals(this.availableAttendeesCount, eventRequestDTO.availableAttendeesCount) &&
        Objects.equals(this.description, eventRequestDTO.description) &&
        Objects.equals(this.category, eventRequestDTO.category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, date, availableAttendeesCount, description, category);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventRequestDTO {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    availableAttendeesCount: ").append(toIndentedString(availableAttendeesCount)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
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


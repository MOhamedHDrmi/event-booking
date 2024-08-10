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
 * EventResponseDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-10T15:36:30.869585+03:00[Africa/Cairo]")
public class EventResponseDTO {

  @JsonProperty("id")
  private Long id;

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

  public EventResponseDTO id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", required = false)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public EventResponseDTO name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  
  @Schema(name = "name", required = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public EventResponseDTO date(LocalDate date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
  */
  @Valid 
  @Schema(name = "date", required = false)
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public EventResponseDTO availableAttendeesCount(Integer availableAttendeesCount) {
    this.availableAttendeesCount = availableAttendeesCount;
    return this;
  }

  /**
   * Get availableAttendeesCount
   * @return availableAttendeesCount
  */
  
  @Schema(name = "availableAttendeesCount", required = false)
  public Integer getAvailableAttendeesCount() {
    return availableAttendeesCount;
  }

  public void setAvailableAttendeesCount(Integer availableAttendeesCount) {
    this.availableAttendeesCount = availableAttendeesCount;
  }

  public EventResponseDTO description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", required = false)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public EventResponseDTO category(Category category) {
    this.category = category;
    return this;
  }

  /**
   * Get category
   * @return category
  */
  @Valid 
  @Schema(name = "category", required = false)
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
    EventResponseDTO eventResponseDTO = (EventResponseDTO) o;
    return Objects.equals(this.id, eventResponseDTO.id) &&
        Objects.equals(this.name, eventResponseDTO.name) &&
        Objects.equals(this.date, eventResponseDTO.date) &&
        Objects.equals(this.availableAttendeesCount, eventResponseDTO.availableAttendeesCount) &&
        Objects.equals(this.description, eventResponseDTO.description) &&
        Objects.equals(this.category, eventResponseDTO.category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, date, availableAttendeesCount, description, category);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventResponseDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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


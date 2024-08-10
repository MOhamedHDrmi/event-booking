package com.musalasoft.eventbooking.core.domain.enums;

import com.musalasoft.eventbooking.core.exception.InvalidParameterValueException;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum Category {
    CONCERT("Concert"),

    CONFERENCE("Conference"),

    GAME("Game");

    final String value;

    Category(String value) {
        this.value = value;
    }

    public static Category fromValue(String value) {
        return Arrays.stream(Category.values())
              .filter(typeEnum -> Objects.equals(typeEnum.value, value)).findFirst()
              .orElseThrow(() -> new InvalidParameterValueException("Invalid Category !!"));
    }
}

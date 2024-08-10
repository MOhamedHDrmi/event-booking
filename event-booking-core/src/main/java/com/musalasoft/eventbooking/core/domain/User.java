package com.musalasoft.eventbooking.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    Long userId;
    String name;
    String password;
    String email;
}

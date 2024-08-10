package com.musalasoft.eventbooking.core.sql.command;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserSqlCommand {

    String name;
    String password;
    String email;
}

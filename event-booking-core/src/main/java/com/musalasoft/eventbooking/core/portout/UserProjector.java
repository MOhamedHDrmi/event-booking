package com.musalasoft.eventbooking.core.portout;

import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.sql.command.CreateUserSqlCommand;

public interface UserProjector {
    User createUser(CreateUserSqlCommand command);

    User findByName(String name);

    User findByEmailAndPassword(String email, String password);

    User findById(Long userId);
}

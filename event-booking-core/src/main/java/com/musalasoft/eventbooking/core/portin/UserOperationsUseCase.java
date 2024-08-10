package com.musalasoft.eventbooking.core.portin;

import com.musalasoft.eventbooking.core.api.command.CreateUserCommand;
import com.musalasoft.eventbooking.core.domain.User;

public interface UserOperationsUseCase {

    void create(CreateUserCommand command);

    User findByName(String name);

    User findById(Long userId);

}

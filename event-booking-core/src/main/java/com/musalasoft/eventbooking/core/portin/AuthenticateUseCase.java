package com.musalasoft.eventbooking.core.portin;

import com.musalasoft.eventbooking.core.api.command.AuthenticateCommand;

public interface AuthenticateUseCase {

    String authenticate(AuthenticateCommand authenticateCommand);
}

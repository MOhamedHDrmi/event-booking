package com.musalasoft.eventbooking.core.adapter;

import com.musalasoft.eventbooking.core.api.command.CreateUserCommand;
import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.mapper.UserDomainMapper;
import com.musalasoft.eventbooking.core.portin.UserOperationsUseCase;
import com.musalasoft.eventbooking.core.portout.UserProjector;
import com.musalasoft.eventbooking.core.sql.command.CreateUserSqlCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserAdapter implements UserOperationsUseCase {

    private final UserProjector projector;
    private final UserDomainMapper mapper;

    UserAdapter(UserProjector userProjector, UserDomainMapper userDomainMapper) {
        this.projector = userProjector;
        this.mapper = userDomainMapper;
    }

    @Override
    public void create(CreateUserCommand command) {
        User user = this.mapper.toUser(command);

        CreateUserSqlCommand sqlUserCommand = this.mapper.toCreateSqlUserCommand(user);

        log.info("Create new user with email: {}", sqlUserCommand.getEmail());

        this.projector.createUser(sqlUserCommand);
    }

    @Override
    public User findByName(String name) {
        return this.projector.findByName(name);
    }

    @Override
    public User findById(Long userId) {
        return this.projector.findById(userId);
    }
}

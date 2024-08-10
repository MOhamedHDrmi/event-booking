package com.musalasoft.eventbooking.sql.adapter;

import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.exception.ResourceNotFoundException;
import com.musalasoft.eventbooking.core.portout.UserProjector;
import com.musalasoft.eventbooking.core.sql.command.CreateUserSqlCommand;
import com.musalasoft.eventbooking.sql.entity.UserEntity;
import com.musalasoft.eventbooking.sql.mapper.UserSqlMapper;
import com.musalasoft.eventbooking.sql.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPersistenceAdapter implements UserProjector {

    private final UserRepository repository;
    private final UserSqlMapper mapper;

    UserPersistenceAdapter(UserRepository userRepository, UserSqlMapper mapper) {
        this.repository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public User createUser(CreateUserSqlCommand command) {
        UserEntity userEntity = this.mapper.toUserEntity(command);

        userEntity = this.repository.save(userEntity);

        return this.mapper.toUser(userEntity);
    }

    @Override
    public User findByName(String name) {
        UserEntity userEntity = this.repository.findUserEntityByName(name);
        return this.mapper.toUser(userEntity);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        UserEntity userEntity = this.repository.findUserEntityByEmailAndPassword(email, password);
        return this.mapper.toUser(userEntity);
    }

    @Override
    public User findById(Long userId) {
        Optional<UserEntity> userEntity = this.repository.findById(userId);
        return userEntity.map(mapper::toUser)
                         .orElseThrow(() -> new ResourceNotFoundException(String.format("Can't Find User with Id: %s", userId)));
    }
}

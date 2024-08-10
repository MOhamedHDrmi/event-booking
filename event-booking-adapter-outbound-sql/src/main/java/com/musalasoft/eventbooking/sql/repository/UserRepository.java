package com.musalasoft.eventbooking.sql.repository;

import com.musalasoft.eventbooking.sql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserEntityByName(String name);

    UserEntity findUserEntityByEmailAndPassword(String email, String password);
}

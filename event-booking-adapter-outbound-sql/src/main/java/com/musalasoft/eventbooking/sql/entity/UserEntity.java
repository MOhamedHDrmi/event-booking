package com.musalasoft.eventbooking.sql.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity(name = "users")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    Long userId;

    @Size(min = 1, max = 100)
    String name;

    @Size(min = 8)
    String password;

    @Email
    @Column(unique = true)
    String email;
}

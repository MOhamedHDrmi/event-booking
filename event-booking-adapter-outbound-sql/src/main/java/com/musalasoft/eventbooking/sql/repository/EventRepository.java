package com.musalasoft.eventbooking.sql.repository;

import com.musalasoft.eventbooking.sql.entity.EventEntity;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long>, QuerydslPredicateExecutor<EventEntity> {

    @Override
    List<EventEntity> findAll(Predicate predicate);
}

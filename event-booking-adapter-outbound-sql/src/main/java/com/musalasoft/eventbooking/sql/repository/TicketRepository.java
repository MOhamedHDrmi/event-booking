package com.musalasoft.eventbooking.sql.repository;

import com.musalasoft.eventbooking.sql.entity.TicketEntity;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long>, QuerydslPredicateExecutor<TicketEntity> {

    List<TicketEntity> findByEvent_EventId(Long eventId);

    List<TicketEntity> findByUser_UserId(Long userId);

    @Override
    List<TicketEntity> findAll(Predicate predicate);
}

package com.musalasoft.eventbooking.sql.repository;

import com.musalasoft.eventbooking.sql.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntity, Long> {
}

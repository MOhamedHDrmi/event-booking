package com.musalasoft.eventbooking.sql.mapper;

import com.musalasoft.eventbooking.core.job.audit.Audit;
import com.musalasoft.eventbooking.sql.entity.AuditEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.Month;

class AuditSqlMapperTest {

    private AuditSqlMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = Mappers.getMapper(AuditSqlMapper.class);
    }

    @Test
    void toAuditEntity_WithValidInput() {
        // given
        Audit audit = Audit.builder()
                           .auditDate(LocalDateTime.of(2024, Month.FEBRUARY, 14, 16, 16, 1))
                           .message("message")
                           .build();

        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setAuditDate(LocalDateTime.of(2024, Month.FEBRUARY, 14, 16, 16, 1));
        auditEntity.setMessage("message");

        AuditEntity acutal = this.mapper.toAuditEntity(audit);

        // assert
        Assertions.assertEquals(auditEntity, acutal);
    }

    @Test
    void toAuditEntity_WithNullInput() {
        Assertions.assertNull(this.mapper.toAuditEntity(null));
    }
}
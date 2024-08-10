package com.musalasoft.eventbooking.sql.adapter;

import com.musalasoft.eventbooking.core.job.audit.Audit;
import com.musalasoft.eventbooking.sql.entity.AuditEntity;
import com.musalasoft.eventbooking.sql.mapper.AuditSqlMapper;
import com.musalasoft.eventbooking.sql.repository.AuditRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.Month;


class AuditAdapterTest {

    @Mock
    AuditRepository auditRepository;
    @Mock
    AuditSqlMapper mapper;
    @InjectMocks
    AuditAdapter auditAdapter;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void logAudit() {
        // given
        Audit audit = Audit.builder()
                           .auditDate(LocalDateTime.of(2024, Month.FEBRUARY, 14, 16, 16, 1))
                           .message("message")
                           .build();

        // when
        Mockito.when(this.mapper.toAuditEntity(ArgumentMatchers.any()))
               .thenReturn(new AuditEntity());
        Mockito.when(this.auditRepository.save(ArgumentMatchers.any(AuditEntity.class)))
               .thenReturn(new AuditEntity());

        // act
        this.auditAdapter.logAudit(audit);

        // verify
        Mockito.verify(this.auditRepository, Mockito.times(1)).save(ArgumentMatchers.any());
        Mockito.verify(this.mapper, Mockito.times(1)).toAuditEntity(ArgumentMatchers.any());
    }
}
package com.musalasoft.eventbooking.sql.adapter;

import com.musalasoft.eventbooking.core.job.audit.Audit;
import com.musalasoft.eventbooking.core.portout.AuditProjector;
import com.musalasoft.eventbooking.sql.entity.AuditEntity;
import com.musalasoft.eventbooking.sql.mapper.AuditSqlMapper;
import com.musalasoft.eventbooking.sql.repository.AuditRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditAdapter implements AuditProjector {

    private final AuditRepository auditRepository;
    private final AuditSqlMapper mapper;

    public AuditAdapter(AuditRepository auditRepository, AuditSqlMapper mapper) {
        this.auditRepository = auditRepository;
        this.mapper = mapper;
    }

    @Override
    public void logAudit(Audit audit) {
        AuditEntity auditEntity = this.mapper.toAuditEntity(audit);

        this.auditRepository.save(auditEntity);
    }
}

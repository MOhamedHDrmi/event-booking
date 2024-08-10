package com.musalasoft.eventbooking.sql.mapper;

import com.musalasoft.eventbooking.core.job.audit.Audit;
import com.musalasoft.eventbooking.sql.entity.AuditEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditSqlMapper {

    AuditEntity toAuditEntity(Audit audit);
}

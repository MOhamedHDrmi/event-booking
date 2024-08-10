package com.musalasoft.eventbooking.core.portout;

import com.musalasoft.eventbooking.core.job.audit.Audit;

public interface AuditProjector {

    void logAudit(Audit audit);
}

package com.musalasoft.eventbooking.core.job.audit;

import com.musalasoft.eventbooking.core.portout.AuditProjector;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditAspect {

    private final AuditProjector auditProjector;

    public AuditAspect(AuditProjector auditProjector) {this.auditProjector = auditProjector;}

    @Pointcut("execution(* com.musalasoft.eventbooking.core.job.ScheduledTask.sendUpcomingEventNotifications())")
    public void auditPointCut(){}

    @Before("auditPointCut()")
    public void logBefore(JoinPoint joinPoint) {
        String msg = String.format("Method %s is Called, Start Notify Users For Upcoming Events", joinPoint.getSignature().getName());

        this.auditProjector.logAudit(this.buildAuditWithMessage(msg));
    }

    @After("auditPointCut()")
    public void logAfter(JoinPoint joinPoint) {
        String msg = String.format("Method %s Success, Notify Users For Upcoming Events Done", joinPoint.getSignature().getName());

        this.auditProjector.logAudit(this.buildAuditWithMessage(msg));
    }

    @AfterThrowing("auditPointCut()")
    public void logThrow(JoinPoint joinPoint) {
        String msg = String.format("Method %s Throws, Unable to Send Notifications to Users", joinPoint.getSignature().getName());

        this.auditProjector.logAudit(this.buildAuditWithMessage(msg));
    }

    private Audit buildAuditWithMessage(String msg) {
        return Audit.builder()
                    .auditDate(LocalDateTime.now())
                    .message(msg)
                    .build();
    }
}

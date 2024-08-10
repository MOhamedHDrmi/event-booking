package com.musalasoft.eventbooking.core.job.audit;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Audit {

    LocalDateTime auditDate;
    String message;
}

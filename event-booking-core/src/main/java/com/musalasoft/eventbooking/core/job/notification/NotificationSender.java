package com.musalasoft.eventbooking.core.job.notification;

import com.musalasoft.eventbooking.core.domain.Ticket;

public interface NotificationSender {

    void send(Ticket ticket);
}

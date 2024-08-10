package com.musalasoft.eventbooking.core.job.notification;

import com.musalasoft.eventbooking.core.domain.Event;
import com.musalasoft.eventbooking.core.domain.Ticket;
import com.musalasoft.eventbooking.core.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailNotificationSender implements NotificationSender {

    @Override
    public void send(Ticket ticket) {
        User user = ticket.getUser();
        Event event = ticket.getEvent();

        String message = this.buildNotificationMessage(user, event);

        log.info("Send notification email for event {} to {}, notification message [{}]", event, user.getEmail(), message);
        log.info("Notification email sent !!");
    }

    private String buildNotificationMessage(User user, Event event) {
        StringBuilder message = new StringBuilder();
        message.append("Hello ")
               .append(user.getName())
               .append(",\n")
               .append("You have upcoming '")
               .append(event.getCategory())
               .append("' event for today with title '")
               .append(event.getName()).append("'.\n");

        if (event.getDescription() != null) {
            message.append("The event description: \n")
                   .append(event.getDescription());
        }

        return message.toString();
    }
}

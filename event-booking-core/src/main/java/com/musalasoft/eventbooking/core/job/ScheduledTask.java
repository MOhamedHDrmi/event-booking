package com.musalasoft.eventbooking.core.job;

import com.musalasoft.eventbooking.core.adapter.TicketReservationAdapter;
import com.musalasoft.eventbooking.core.api.filter.ReservationFilter;
import com.musalasoft.eventbooking.core.domain.Ticket;
import com.musalasoft.eventbooking.core.job.notification.NotificationSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Component
@Slf4j
public class ScheduledTask {

    private final TicketReservationAdapter reservationAdapter;
    private final List<NotificationSender> notificationSenders;

    public ScheduledTask(TicketReservationAdapter reservationAdapter, List<NotificationSender> notificationSenders) {
        this.reservationAdapter = reservationAdapter;
        this.notificationSenders = notificationSenders;
    }

    /**
     * Periodically sends notifications for upcoming events.
     * This method is scheduled to run at a fixed interval 1 AM.
     * It retrieves a list of tickets for reservations with the current date and sends notifications
     * using the configured notification senders for each ticket.
     *
     * <p>
     * The method performs the following steps:
     * <ol>
     *   <li>Logs an informational message indicating the start of sending notifications.</li>
     *   <li>Creates a {@link ReservationFilter} with the reservation date set to the current date.</li>
     *   <li>Retrieves a list of tickets using the {@link TicketReservationAdapter} and the created filter.</li>
     *   <li>Iterates over each configured {@link NotificationSender} and sends notifications for each ticket.</li>
     *   <li>Logs a message indicating that all notifications have been sent successfully.</li>
     * </ol>
     * </p>
     *
     * @see Scheduled
     * @see ReservationFilter
     * @see TicketReservationAdapter
     * @see NotificationSender
     * @see Ticket
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void sendUpcomingEventNotifications() {
        log.info("Start sending notifications");

        ReservationFilter filter = ReservationFilter.builder()
                                                    .reservationDate(LocalDate.now())
                                                    .build();

        List<Ticket> ticketList = this.reservationAdapter.getAll(filter);

        notificationSenders.forEach(notificationSender -> Stream.ofNullable(ticketList)
                                                            .flatMap(Collection::stream)
                                                            .forEach(notificationSender::send));

        log.info("All notifications sent successfully");
    }
}

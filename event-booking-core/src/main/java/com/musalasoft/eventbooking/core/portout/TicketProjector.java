package com.musalasoft.eventbooking.core.portout;

import com.musalasoft.eventbooking.core.api.filter.ReservationFilter;
import com.musalasoft.eventbooking.core.domain.Ticket;
import com.musalasoft.eventbooking.core.sql.command.CancelTicketSqlCommand;
import com.musalasoft.eventbooking.core.sql.command.ReserveTicketSqlCommand;

import java.util.List;

public interface TicketProjector {

    void reserveEventTicket(ReserveTicketSqlCommand reserveTicketSqlCommand);

    void deleteEventReservationTicket(CancelTicketSqlCommand command);

    List<Ticket> getAllBooked(Long userId);

    List<Ticket> findAll(ReservationFilter filter);
}

package com.musalasoft.eventbooking.core.portin;

import com.musalasoft.eventbooking.core.api.command.CancelTicketCommand;
import com.musalasoft.eventbooking.core.api.command.ReserveEventTicketCommand;
import com.musalasoft.eventbooking.core.api.filter.ReservationFilter;
import com.musalasoft.eventbooking.core.domain.Ticket;

import java.util.List;

public interface TicketReservationUseCase {

    void reserveEvent(ReserveEventTicketCommand reserveEventTicketCommand);

    void cancelEventReservation(CancelTicketCommand command);

    List<Ticket> getAllBooked();

    List<Ticket> getAll(ReservationFilter filter);
}

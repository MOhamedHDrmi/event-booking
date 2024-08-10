package com.musalasoft.eventbooking.core.adapter;

import com.musalasoft.eventbooking.core.api.command.CancelTicketCommand;
import com.musalasoft.eventbooking.core.api.command.ReserveEventTicketCommand;
import com.musalasoft.eventbooking.core.api.filter.ReservationFilter;
import com.musalasoft.eventbooking.core.domain.Ticket;
import com.musalasoft.eventbooking.core.domain.User;
import com.musalasoft.eventbooking.core.mapper.TicketReservationDomainMapper;
import com.musalasoft.eventbooking.core.portin.TicketReservationUseCase;
import com.musalasoft.eventbooking.core.portout.TicketProjector;
import com.musalasoft.eventbooking.core.sql.command.CancelTicketSqlCommand;
import com.musalasoft.eventbooking.core.sql.command.ReserveTicketSqlCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TicketReservationAdapter implements TicketReservationUseCase {

    private final TicketReservationDomainMapper domainMapper;
    private final UserAdapter userAdapter;
    private final TicketProjector ticketProjector;

    public TicketReservationAdapter(TicketReservationDomainMapper domainMapper, UserAdapter userAdapter, TicketProjector ticketProjector) {
        this.domainMapper = domainMapper;
        this.userAdapter = userAdapter;
        this.ticketProjector = ticketProjector;
    }

    @Override
    public void reserveEvent(ReserveEventTicketCommand reserveEventTicketCommand) {
        User user = this.getAuthenticatedUser();

        ReserveTicketSqlCommand reserveTicketSqlCommand = this.domainMapper.toReserveSqlEventCommand(user.getUserId(), reserveEventTicketCommand);

        log.debug("Reserve event command: {}", reserveTicketSqlCommand);

        this.ticketProjector.reserveEventTicket(reserveTicketSqlCommand);
    }

    @Override
    public void cancelEventReservation(CancelTicketCommand command) {
        CancelTicketSqlCommand sqlReservationCommand = this.domainMapper.toCancelSqlReservationCommand(command);

        log.debug("Cancel reservation with Id: {}", sqlReservationCommand.getTicketId());

        this.ticketProjector.deleteEventReservationTicket(sqlReservationCommand);
    }

    @Override
    public List<Ticket> getAllBooked() {
        User user = this.getAuthenticatedUser();

        log.info("Fetch reserved events for userId: {}", user.getUserId());

        return this.ticketProjector.getAllBooked(user.getUserId());
    }

    @Override
    public List<Ticket> getAll(ReservationFilter filter) {
        return this.ticketProjector.findAll(filter);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = ((UserDetails) authentication.getPrincipal()).getUsername();

        return this.userAdapter.findByName(name);
    }
}

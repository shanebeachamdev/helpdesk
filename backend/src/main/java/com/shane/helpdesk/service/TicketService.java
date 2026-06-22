package com.shane.helpdesk.service;

import com.shane.helpdesk.dto.TicketRequest;
import com.shane.helpdesk.entity.Ticket;

import java.util.List;

public interface TicketService {

    Ticket createTicket(TicketRequest request, String userEmail);

    List<Ticket> getMyTickets(String userEmail);

    Ticket getTicketById(Long id, String userEmail);

    List<Ticket> getAllTickets();
}
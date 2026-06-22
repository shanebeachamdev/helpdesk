package com.shane.helpdesk.service.impl;

import com.shane.helpdesk.dto.TicketRequest;
import com.shane.helpdesk.entity.Ticket;
import com.shane.helpdesk.entity.User;
import com.shane.helpdesk.repository.TicketRepository;
import com.shane.helpdesk.repository.UserRepository;
import com.shane.helpdesk.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketServiceImpl(TicketRepository ticketRepository,
                             UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Ticket createTicket(TicketRequest request, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCreatedBy(user);

        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> getMyTickets(String userEmail) {
        return ticketRepository.findByCreatedBy_Email(userEmail);
    }

    @Override
    public Ticket getTicketById(Long id, String userEmail) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (!ticket.getCreatedBy().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized");
        }

        return ticket;
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
package com.shane.helpdesk.controller;

import com.shane.helpdesk.dto.TicketRequest;
import com.shane.helpdesk.entity.Ticket;
import com.shane.helpdesk.entity.TicketStatus;
import com.shane.helpdesk.entity.User;
import com.shane.helpdesk.repository.TicketRepository;
import com.shane.helpdesk.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketController(TicketRepository ticketRepository,
                            UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public Ticket createTicket(@RequestBody TicketRequest request,
                               Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }

        // JWT filter sets principal = email (String)
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedBy(user);

        return ticketRepository.save(ticket);
    }

    @GetMapping("/my")
    public List<Ticket> myTickets(Authentication authentication) {

        String email = authentication.getName();
        if(email == null) {
            throw new RuntimeException("Invalid authentication");
        }

        return ticketRepository.findByCreatedBy_Email(email);
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @PutMapping("/{id}/status")
    public Ticket updateStatus(
            @PathVariable Long id,
            @RequestParam TicketStatus status
    ) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(status);

        return ticketRepository.save(ticket);
    }
}
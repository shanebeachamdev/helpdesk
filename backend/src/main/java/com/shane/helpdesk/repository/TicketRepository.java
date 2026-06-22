package com.shane.helpdesk.repository;

import com.shane.helpdesk.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByCreatedBy_Email(String email);

    List<Ticket> findByStatus(TicketStatus status);
}
package com.shane.helpdesk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable=false)
    private User createdBy;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @ManyToOne
    private User assignedTo;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
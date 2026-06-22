package com.shane.helpdesk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * @Entity tells JPA/Hibernate:
 *
 * "Create a database table from this class."
 */
@Entity

/*
 * Lombok generates getters/setters automatically.
 */
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    /*
     * Primary Key
     *
     * Auto-generated ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * User's first name.
     */
    @Column(nullable = false)
    private String firstName;

    /*
     * User's last name.
     */
    @Column(nullable = false)
    private String lastName;

    /*
     * Email must be unique.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /*
     * Password will eventually be encrypted.
     */
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    /*
     * Store role as text:
     *
     * USER
     * TECHNICIAN
     * ADMIN
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /*
     * Timestamp for when account was created.
     */
    private LocalDateTime createdAt;

    /*
     * Automatically runs before saving.
     */
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
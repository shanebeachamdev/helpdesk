package com.shane.helpdesk.repository;

import com.shane.helpdesk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * JpaRepository gives us:
 *
 * save()
 * findById()
 * findAll()
 * delete()
 *
 * without writing SQL.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /*
     * Spring automatically generates query:
     *
     * SELECT * FROM users WHERE email = ?
     */
    Optional<User> findByEmail(String email);
}
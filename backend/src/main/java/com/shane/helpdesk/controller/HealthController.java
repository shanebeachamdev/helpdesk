package com.shane.helpdesk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * This is a simple test controller.
 *
 * Purpose:
 * - Verify Spring Boot is working
 * - Confirm routing works
 */
@RestController
public class HealthController {

    /*
     * GET endpoint for testing server status
     *
     * URL: http://localhost:8080/api/health
     */
    @GetMapping("/api/health")
    public String healthCheck() {
        return "Helpdesk API is running!";
    }
}
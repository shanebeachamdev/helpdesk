package com.shane.helpdesk.controller;

import com.shane.helpdesk.dto.AuthResponse;
import com.shane.helpdesk.dto.LoginRequest;
import com.shane.helpdesk.dto.RegisterRequest;
import com.shane.helpdesk.entity.Role;
import com.shane.helpdesk.entity.User;
import com.shane.helpdesk.repository.UserRepository;
import com.shane.helpdesk.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        System.out.println("LOGIN ATTEMPT: " + request.getEmail());
        
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        System.out.println("AUTH SUCCESS");
        
        User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"
        ));
        
        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );
        
        return new AuthResponse(token);
}

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email already exists"
            );
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new AuthResponse(token);
    }
}
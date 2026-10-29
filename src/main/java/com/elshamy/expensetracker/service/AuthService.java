package com.elshamy.expensetracker.service;

import com.elshamy.expensetracker.dto.LoginRequestDTO;
import com.elshamy.expensetracker.dto.LoginResponseDTO;
import com.elshamy.expensetracker.dto.RegisterRequestDTO;
import com.elshamy.expensetracker.dto.RegisterResponseDTO;
import com.elshamy.expensetracker.entity.User;
import com.elshamy.expensetracker.exception.ResourceAlreadyExistsException;
import com.elshamy.expensetracker.exception.ResourceNotFoundException;
import com.elshamy.expensetracker.repository.UserRepository;
import com.elshamy.expensetracker.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        String usernameOrEmail = request.usernameOrEmail();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usernameOrEmail,
                        request.password()
                )
        );

        User user = userRepository
                .findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username/email", usernameOrEmail));

        String token = jwtService.generateAccessToken(user.getUsername());

        return new LoginResponseDTO(
                user.getUsername(),
                user.getEmail(),
                token
        );
    }


    public RegisterResponseDTO register(RegisterRequestDTO request) {

        if (userRepository.existsByUsername(request.username())) {
            throw new ResourceAlreadyExistsException("Username is already taken");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistsException("Email is already registered");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        User savedUser = userRepository.save(user);

        return new RegisterResponseDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getCreatedAt()
        );
    }
}
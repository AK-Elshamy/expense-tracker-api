package com.elshamy.expensetracker.controller;

import com.elshamy.expensetracker.dto.LoginRequestDTO;
import com.elshamy.expensetracker.dto.LoginResponseDTO;
import com.elshamy.expensetracker.dto.RegisterRequestDTO;
import com.elshamy.expensetracker.dto.RegisterResponseDTO;
import com.elshamy.expensetracker.security.CustomUserPrincipal;
import com.elshamy.expensetracker.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        RegisterResponseDTO response = authService.register(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {
        CustomUserPrincipal principal =
                (CustomUserPrincipal) authentication.getPrincipal();

        return Map.of(
                "id", principal.getId(),
                "username", principal.getUsername()
        );
    }

}
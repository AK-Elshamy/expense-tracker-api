package com.elshamy.expensetracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Must be a valid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
        String password
) {}
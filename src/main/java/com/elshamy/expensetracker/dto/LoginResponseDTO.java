package com.elshamy.expensetracker.dto;

public record LoginResponseDTO(
        String username,
        String email,
        String token
) {
}
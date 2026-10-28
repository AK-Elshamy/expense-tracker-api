package com.elshamy.expensetracker.dto;

import com.elshamy.expensetracker.entity.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExpenseResponseDTO(
        Long id,
        BigDecimal amount,
        Category category,
        String description,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate expenseDate,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime createdAt
) {}
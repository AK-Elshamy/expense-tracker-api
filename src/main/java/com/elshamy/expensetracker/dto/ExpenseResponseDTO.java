package com.elshamy.expensetracker.dto;

import com.elshamy.expensetracker.entity.Category;
import com.elshamy.expensetracker.entity.Expense;
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
) {
        public static ExpenseResponseDTO fromExpense(Expense expense) {
                return new ExpenseResponseDTO(
                        expense.getId(),
                        expense.getAmount(),
                        expense.getCategory(),
                        expense.getDescription(),
                        expense.getExpenseDate(),
                        expense.getCreatedAt()
                );
        }
}
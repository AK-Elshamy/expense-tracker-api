package com.elshamy.expensetracker.dto;

import com.elshamy.expensetracker.entity.Category;

import java.math.BigDecimal;

public record CategoryExpenseDTO(
        Category category,
        BigDecimal totalAmount,
        Long count,
        Double percentage
) {
    public CategoryExpenseDTO(Category category, BigDecimal totalAmount, Long count) {
        this(category, totalAmount, count, null);
    }
}
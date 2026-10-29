package com.elshamy.expensetracker.repository;

import com.elshamy.expensetracker.entity.Category;
import com.elshamy.expensetracker.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {


    Page<Expense> findByUserId(Long userId, Pageable pageable);

    Optional<Expense> findByIdAndUserId(Long id, Long userId);


    Page<Expense> findByUserIdAndCategory(Long userId, Category category);

    Page<Expense> findByUserIdAndExpenseDateBetween(Long userId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
package com.elshamy.expensetracker.service;

import com.elshamy.expensetracker.dto.ExpenseRequestDTO;
import com.elshamy.expensetracker.dto.ExpenseResponseDTO;
import com.elshamy.expensetracker.entity.Expense;
import com.elshamy.expensetracker.entity.User;
import com.elshamy.expensetracker.exception.ResourceNotFoundException;
import com.elshamy.expensetracker.repository.ExpenseRepository;
import com.elshamy.expensetracker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final int PAGE_SIZE = 5;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    private User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User", "username", username));
    }

    public ExpenseResponseDTO createExpense(ExpenseRequestDTO expenseRequestDTO) {
        User user = getAuthenticatedUser();

        Expense expense = new Expense();
        expense.setUser(user);
        expense.setAmount(expenseRequestDTO.amount());
        expense.setDescription(expenseRequestDTO.description());
        expense.setCategory(expenseRequestDTO.category());
        expense.setExpenseDate(expenseRequestDTO.expenseDate());

        Expense expenseSaved = expenseRepository.save(expense);
        return ExpenseResponseDTO.fromExpense(expenseSaved);
    }

    public Page<ExpenseResponseDTO> getExpenses(int page) {
        User user = getAuthenticatedUser();

        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Expense> expenses = expenseRepository.findByUserId(user.getId(), pageable);

        return expenses.map(ExpenseResponseDTO::fromExpense);
    }
}
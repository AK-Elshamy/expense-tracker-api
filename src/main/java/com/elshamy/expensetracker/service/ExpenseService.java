package com.elshamy.expensetracker.service;

import com.elshamy.expensetracker.dto.ExpenseRequestDTO;
import com.elshamy.expensetracker.dto.ExpenseResponseDTO;
import com.elshamy.expensetracker.entity.Expense;
import com.elshamy.expensetracker.entity.User;
import com.elshamy.expensetracker.exception.ResourceNotFoundException;
import com.elshamy.expensetracker.repository.ExpenseRepository;
import com.elshamy.expensetracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public ExpenseResponseDTO createExpense(ExpenseRequestDTO expenseRequestDTO){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User", "username", username));
        Expense expense = new Expense();
        expense.setUser(user);
        expense.setAmount(expenseRequestDTO.amount());
        expense.setDescription(expenseRequestDTO.description());
        expense.setCategory(expenseRequestDTO.category());
        expense.setExpenseDate(expenseRequestDTO.expenseDate());

        Expense expenseSaved = expenseRepository.save(expense);
        return ExpenseResponseDTO.fromExpense(expenseSaved);

    }
}
package com.elshamy.expensetracker.service;

import com.elshamy.expensetracker.dto.ExpenseRequestDTO;
import com.elshamy.expensetracker.dto.ExpenseResponseDTO;
import com.elshamy.expensetracker.dto.PageResponseDTO;
import com.elshamy.expensetracker.entity.Expense;
import com.elshamy.expensetracker.entity.User;
import com.elshamy.expensetracker.exception.ResourceNotFoundException;
import com.elshamy.expensetracker.repository.ExpenseRepository;
import com.elshamy.expensetracker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private Expense getExpenseAndVerifyOwnership(Long expenseId) {
        User user = getAuthenticatedUser();

        Expense expense = expenseRepository.findById(expenseId).orElseThrow(
                () -> new ResourceNotFoundException("Expense", "id", expenseId)
        );

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not have permission to modify or access this expense");
        }

        return expense;
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


    public PageResponseDTO<ExpenseResponseDTO> getExpenses(int page) {
        User user = getAuthenticatedUser();

        Pageable pageable = PageRequest.of(page, PAGE_SIZE);

        Page<Expense> expenses =
                expenseRepository.findByUserId(user.getId(), pageable);

        Page<ExpenseResponseDTO> pageResponse =
                expenses.map(ExpenseResponseDTO::fromExpense);

        return PageResponseDTO.from(pageResponse);
    }

    public ExpenseResponseDTO updateExpense(ExpenseRequestDTO expenseRequestDTO, Long expenseId) {
        Expense expense =getExpenseAndVerifyOwnership(expenseId);
        expense.setAmount(expenseRequestDTO.amount());
        expense.setDescription(expenseRequestDTO.description());
        expense.setCategory(expenseRequestDTO.category());
        expense.setExpenseDate(expenseRequestDTO.expenseDate());

        Expense expenseSaved = expenseRepository.save(expense);
        return ExpenseResponseDTO.fromExpense(expenseSaved);
    }

    public void deleteExpense(Long expenseId) {
        Expense expense = getExpenseAndVerifyOwnership(expenseId);
        expenseRepository.delete(expense);
    }

    public  PageResponseDTO<ExpenseResponseDTO> filterExpenses(
            String period,
            LocalDate startDate,
            LocalDate endDate,
            int page
    ){
        User user = getAuthenticatedUser();
        LocalDate today = LocalDate.now();
        LocalDate from;
        LocalDate to;

        if(period != null){
            to = today;
            switch (period.toLowerCase()){
                case "week" -> from = today.minusWeeks(6);
                case "month" -> from = today.minusMonths(1);
                case "last3months" -> from = today.minusMonths(3);
                default -> throw new IllegalArgumentException("Invalid period. Use Week, month, or last3months");
            }
        }else{
            if(startDate == null || endDate == null){
                throw new IllegalArgumentException("Provide a valid period or both startDate and endDate");
            }

            if(startDate.isAfter(endDate)){
                throw new IllegalArgumentException("startDate must be before or equal to endDate");
            }
            from = startDate;
            to = endDate;
        }

        Pageable pageable = PageRequest.of(page, PAGE_SIZE);

        Page<Expense> expenses =
                expenseRepository.findByUserIdAndExpenseDateBetween(
                        user.getId(), from, to, pageable
                );

        Page<ExpenseResponseDTO> response = expenses.map(ExpenseResponseDTO::fromExpense);
        return PageResponseDTO.from(response);
}

}
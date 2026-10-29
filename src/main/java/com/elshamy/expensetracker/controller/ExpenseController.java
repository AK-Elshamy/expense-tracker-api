package com.elshamy.expensetracker.controller;

import com.elshamy.expensetracker.dto.ExpenseRequestDTO;
import com.elshamy.expensetracker.dto.ExpenseResponseDTO;
import com.elshamy.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(@Valid @RequestBody ExpenseRequestDTO expenseRequestDTO) {
        ExpenseResponseDTO responseDTO = expenseService.createExpense(expenseRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
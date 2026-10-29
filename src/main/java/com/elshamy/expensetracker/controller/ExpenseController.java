package com.elshamy.expensetracker.controller;

import com.elshamy.expensetracker.dto.ExpenseRequestDTO;
import com.elshamy.expensetracker.dto.ExpenseResponseDTO;
import com.elshamy.expensetracker.dto.PageResponseDTO;
import com.elshamy.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@Validated
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

    @GetMapping()
    public PageResponseDTO<ExpenseResponseDTO> getExpenses(
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Page number cannot be negative")
             int page
    ) {
        return expenseService.getExpenses(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequestDTO expenseRequestDTO) {
        ExpenseResponseDTO responseDTO = expenseService.updateExpense(expenseRequestDTO, id);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
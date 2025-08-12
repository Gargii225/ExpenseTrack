package com.expensetracker.controller;
import com.expensetracker.entity.Expense;
import com.expensetracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    // POST /api/expenses/add?userEmail=...
    @PostMapping("/add")
    public ResponseEntity<?> addExpense(@RequestParam String userEmail, @RequestBody Expense expense) {
        try {
            Expense saved = expenseService.addExpense(userEmail, expense);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // GET /api/expenses/list?userEmail=...
    @GetMapping("/list")
    public ResponseEntity<List<Expense>> listByUser(@RequestParam String userEmail) {
        return ResponseEntity.ok(expenseService.listByUserEmail(userEmail));
    }

    // GET /api/expenses/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getExpense(@PathVariable Long id) {
        return expenseService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/expenses/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Expense expense) {
        try {
            Expense updated = expenseService.updateExpense(id, expense);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // DELETE /api/expenses/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            expenseService.deleteExpense(id);
            return ResponseEntity.ok("Expense deleted");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Failed to delete");
        }
    }
}

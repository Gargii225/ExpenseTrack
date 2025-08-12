package com.expensetracker.service;



import com.expensetracker.entity.Expense;
import com.expensetracker.entity.User;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepo;
    private final UserRepository userRepo;

    public Expense addExpense(String userEmail, Expense expense) {
        Optional<User> u = userRepo.findByEmail(userEmail);
        if (u.isEmpty()) throw new RuntimeException("User not found");
        expense.setUser(u.get());
        return expenseRepo.save(expense);
    }

    public List<Expense> listByUserEmail(String userEmail) {
        return userRepo.findByEmail(userEmail)
                .map(user -> expenseRepo.findByUserId(user.getId()))
                .orElse(List.of());
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepo.findById(id);
    }

    public Expense updateExpense(Long id, Expense updated) {
        Expense existing = expenseRepo.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
        existing.setName(updated.getName());
        existing.setAmount(updated.getAmount());
        existing.setDate(updated.getDate());
        existing.setDescription(updated.getDescription());
        return expenseRepo.save(existing);
    }

    public void deleteExpense(Long id) {
        expenseRepo.deleteById(id);
    }
}


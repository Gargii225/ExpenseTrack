package com.expensetracker.service;


import com.expensetracker.entity.User;
import com.expensetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;

    public User register(User user) {
        return userRepo.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    // login by email & password (your frontend sends email & password in /login)
    public Optional<User> loginByEmail(String email, String password) {
        return userRepo.findByEmail(email)
                .filter(u -> u.getPassword().equals(password));
    }

}

package com.expensetracker.controller;

import com.expensetracker.entity.User;
import com.expensetracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})
public class AuthController {

    private final UserService userService;



    // frontend posts to http://localhost:8080/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // check email or username exists
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken");
        }
        User saved = userService.register(user);
        // hide password in response
        saved.setPassword(null);
        return ResponseEntity.ok(saved);
    }

    // frontend posts to http://localhost:8080/login with { email, password }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User login) {
        return userService.loginByEmail(login.getEmail(), login.getPassword())
                .<ResponseEntity<?>>map(user -> {
                    user.setPassword(null);
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.status(401).body("Invalid credentials"));
    }


}

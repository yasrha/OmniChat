package com.example.backend.controllers;

import com.example.backend.models.User;
import com.example.backend.responses.LoginResponse;
import com.example.backend.responses.RegisterResponse;
import com.example.backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userAuth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        LoginResponse loginResponse = authService.authenticateUser(user.getUsername(), user.getPassword());

        if (loginResponse.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(loginResponse.getMessage());
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(loginResponse.getMessage());
        }
    }

    @PostMapping(value = "/register",  consumes = "application/json")
    public ResponseEntity<String> signup(@RequestBody User user) {
        RegisterResponse registerResponse = authService.registerUser(user);

        if (registerResponse.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(registerResponse.getMessage());
        } else {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(registerResponse.getMessage());
        }
    }
}

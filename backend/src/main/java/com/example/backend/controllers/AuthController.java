package com.example.backend.controllers;

import com.example.backend.dtos.LoginRequest;
import com.example.backend.models.User;
import com.example.backend.responses.ApiResponse;
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

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<ApiResponse<User>> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (loginResponse.isSuccess()) {
            ApiResponse<User> apiResponse = new ApiResponse<>(loginResponse.getUser(), loginResponse.getMessage());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        } else {
            ApiResponse<User> apiResponse = new ApiResponse<>(null, loginResponse.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(apiResponse);
        }
    }

    @PostMapping(value = "/register",  consumes = "application/json")
    public ResponseEntity<ApiResponse<User>> signup(@RequestBody User user) {
        RegisterResponse registerResponse = authService.registerUser(user);

        if (registerResponse.isSuccess()) {
            ApiResponse<User> apiResponse = new ApiResponse<>(registerResponse.getUser(), registerResponse.getMessage());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        } else {
            ApiResponse<User> apiResponse = new ApiResponse<>(null, registerResponse.getMessage());
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(apiResponse);
        }
    }
}

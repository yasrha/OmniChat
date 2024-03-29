package com.example.backend.services;

import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import com.example.backend.requests.RegisterRequest;
import com.example.backend.responses.LoginResponse;
import com.example.backend.responses.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class AuthService {
    private final MongoTemplate mongoTemplate;
    private UserRepository userRepository;

    @Autowired
    public AuthService(MongoTemplate mongoTemplate, UserRepository userRepository) {
        this.mongoTemplate = mongoTemplate;
        this.userRepository = userRepository;
    }

    public LoginResponse authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null)
            return new LoginResponse(false, "User does not exist", null);

        if (!Objects.equals(password, user.getPassword()))
            return new LoginResponse(false, "Incorrect password", null);

        return new LoginResponse(true, "Login success", user);
    }

    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        User userEmailExists = userRepository.findByEmail(registerRequest.getEmail());
        User usernameExists = userRepository.findByUsername(registerRequest.getUsername());

        if (userEmailExists != null)
            return new RegisterResponse(false, "User with this email already exists", null);

        if (usernameExists != null)
            return new RegisterResponse(false, "User with this username already exists", null);

        User user = new User(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                new ArrayList<>());

        userRepository.save(user);
        return new RegisterResponse(true, "Successfully registered new user", user);
    }
}

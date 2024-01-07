package com.example.backend.services;

import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import com.example.backend.responses.LoginResponse;
import com.example.backend.responses.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

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

    public LoginResponse authenticateUser(String userName, String password) {
        User user = userRepository.findByUsername(userName);

        if (user == null)
            return new LoginResponse(false, "User does not exist");

        if (!Objects.equals(password, user.getPassword()))
            return new LoginResponse(false, "Incorrect password");

        return new LoginResponse(true, "Login success");
    }

    public RegisterResponse registerUser(User user) {
        User userExists = userRepository.findByUsername(user.getUsername());

        if (userExists != null) {
            return new RegisterResponse(false, "User already exists");
        }

        userRepository.save(user);
        return new RegisterResponse(true, "Successfully registered new user");
    }
}

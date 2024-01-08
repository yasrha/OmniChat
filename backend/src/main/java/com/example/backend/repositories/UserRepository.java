package com.example.backend.repositories;

import com.example.backend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    User findByUsername(String username);
    List<User> findByUsernameIn(List<String> usernames);
}

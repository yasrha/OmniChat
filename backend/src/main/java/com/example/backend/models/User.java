package com.example.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Users")
public class User {
    private String username;
    private String email;
    // Will hash eventually
    private String password;
    // Store a list of the chatid of all the chats a user is in
    private List<Integer> chatIds;
}

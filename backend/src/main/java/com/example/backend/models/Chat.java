package com.example.backend.models;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chat {
    private String id;
    private List<User> users;
    private List<Message> messages;
    private Date dateCreated;
}

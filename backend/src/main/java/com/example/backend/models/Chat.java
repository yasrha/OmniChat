package com.example.backend.models;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Chats")
public class Chat {
    private int id;
    private List<String> usernames;
    private List<Message> messages;
    private Date dateCreated;
}

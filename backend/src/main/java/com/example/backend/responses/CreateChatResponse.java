package com.example.backend.responses;

import com.example.backend.models.Chat;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateChatResponse {
    private boolean success;
    private String message;
    private Chat chat;
}

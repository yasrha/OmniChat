package com.example.backend.responses;

import com.example.backend.models.Chat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetChatsResponse {
    private boolean Success;
    private String message;
    private List<Chat> chats;
}

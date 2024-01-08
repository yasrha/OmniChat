package com.example.backend.responses;

import com.example.backend.models.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetChatHistoryResponse {
    private boolean success;
    private String message;
    private List<Message> messages;
}

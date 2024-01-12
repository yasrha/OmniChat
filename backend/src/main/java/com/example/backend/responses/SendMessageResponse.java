package com.example.backend.responses;

import com.example.backend.models.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendMessageResponse {
    private boolean success;
    private String message;
    private Message sentMessage;
}

package com.example.backend.controllers;

import com.example.backend.models.Message;
import com.example.backend.responses.ApiResponse;
import com.example.backend.responses.SendMessageResponse;
import com.example.backend.services.ChatMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chat/{chatId}/sendMessage")
    @SendTo("/topic/chats/{chatId}")
    public ResponseEntity<ApiResponse<Message>> sendMessage(@DestinationVariable int chatId, Message message) {
        SendMessageResponse sendMessageResponse = chatMessageService.saveMessage(chatId, message);

        if (sendMessageResponse.isSuccess()) {
            ApiResponse<Message> apiResponse = new ApiResponse<>(
                    sendMessageResponse.getSentMessage(),
                    sendMessageResponse.getMessage()
            );

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        } else {
            ApiResponse<Message> apiResponse = new ApiResponse<>(
                    null,
                    sendMessageResponse.getMessage()
            );
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(apiResponse);
        }
    }

}

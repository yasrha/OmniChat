package com.example.backend.controllers;

import com.example.backend.dtos.CreateChatRequest;
import com.example.backend.models.Chat;
import com.example.backend.responses.ApiResponse;
import com.example.backend.responses.CreateChatResponse;
import com.example.backend.services.ChatService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<ApiResponse<Chat>> createChat(@RequestBody CreateChatRequest createChatRequest) {
        CreateChatResponse createChatResponse = chatService.createChat(createChatRequest);

        if (createChatResponse.isSuccess()) {
            ApiResponse<Chat> apiResponse = new ApiResponse<>(createChatResponse.getChat(), createChatResponse.getMessage());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        } else {
            ApiResponse<Chat> apiResponse = new ApiResponse<>(null, createChatResponse.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(apiResponse);
        }


    }

}

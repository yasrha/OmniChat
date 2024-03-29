package com.example.backend.controllers;

import com.example.backend.requests.CreateChatRequest;
import com.example.backend.models.Chat;
import com.example.backend.models.Message;
import com.example.backend.responses.*;
import com.example.backend.services.ChatMessageService;
import com.example.backend.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    private final ChatService chatService;
    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatController(ChatService chatService, ChatMessageService chatMessageService) {
        this.chatService = chatService;
        this.chatMessageService = chatMessageService;
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

    @GetMapping("/{chatId}/history")
    public ResponseEntity<ApiResponse<List<Message>>> getChatHistory(@PathVariable int chatId) {
        GetChatHistoryResponse getChatHistoryResponse = chatService.getChatHistory(chatId);

        if (getChatHistoryResponse.isSuccess()) {
            ApiResponse<List<Message>> apiResponse = new ApiResponse<>(
                    getChatHistoryResponse.getMessages(),
                    getChatHistoryResponse.getMessage());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        } else {
            ApiResponse<List<Message>> apiResponse = new ApiResponse<>(null, getChatHistoryResponse.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(apiResponse);
        }
    }

    @PostMapping(value = "/fetchChats", consumes = "application/json")
    public ResponseEntity<ApiResponse<List<Chat>>> getChats(@RequestBody List<Integer> chatIds) {
        GetChatsResponse getChatsResponse = chatService.getChats(chatIds);

        if (getChatsResponse.isSuccess() && getChatsResponse.getChats() != null) {
            ApiResponse<List<Chat>> apiResponse = new ApiResponse<>(
                    getChatsResponse.getChats(),
                    getChatsResponse.getMessage()
            );
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        } else if (getChatsResponse.isSuccess()) {
            ApiResponse<List<Chat>> apiResponse = new ApiResponse<>(
                    null,
                    getChatsResponse.getMessage()
            );
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        } else {
            ApiResponse<List<Chat>> apiResponse = new ApiResponse<>(
                    null,
                    getChatsResponse.getMessage()
            );
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(apiResponse);
        }
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<ApiResponse<List<Message>>> getMessages(@PathVariable int chatId) {
        System.out.println("In getMessages controller");
        GetMessagesResponse getMessagesResponse = chatMessageService.getMessages(chatId);

        if (getMessagesResponse.isSuccess()) {
            ApiResponse<List<Message>> apiResponse = new ApiResponse<>(
                    getMessagesResponse.getMessages(),
                    getMessagesResponse.getMessage()
            );
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        } else {
            ApiResponse<List<Message>> apiResponse = new ApiResponse<>(
                    null,
                    getMessagesResponse.getMessage()
            );
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(apiResponse);
        }
    }

}

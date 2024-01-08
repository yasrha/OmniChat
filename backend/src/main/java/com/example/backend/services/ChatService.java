package com.example.backend.services;

import com.example.backend.requests.CreateChatRequest;
import com.example.backend.models.Chat;
import com.example.backend.models.Message;
import com.example.backend.models.User;
import com.example.backend.repositories.ChatRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.responses.CreateChatResponse;
import com.example.backend.responses.GetChatHistoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private UserRepository userRepository;
    private ChatRepository chatRepository;

    @Autowired
    public ChatService(UserRepository userRepository, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    public CreateChatResponse createChat(CreateChatRequest createChatRequest) {
        // First check if the users exist in the DB
        List<String> requestedUsernames = createChatRequest.getUsernames();
        List<User> existingUsers = userRepository.findByUsernameIn(requestedUsernames);

        Set<String> foundUsernames = existingUsers.stream()
                .map(User::getUsername)
                .collect(Collectors.toSet());

        Set<String> missingUsernames = requestedUsernames.stream()
                .filter(username -> !foundUsernames.contains(username))
                .collect(Collectors.toSet());

        // Handle the case where one or more usernames do not exist
        if (!missingUsernames.isEmpty()) {
            return new CreateChatResponse(
                    false,
                    "Usernames not found: " + String.join(", ", missingUsernames),
                    null
            );
        }

        int chatId = new Random().nextInt(10000);

        Chat newChat = new Chat(
                chatId,
                requestedUsernames,
                new ArrayList<>(),
                new Date()
        );

        chatRepository.save(newChat);

        return new CreateChatResponse(
                true,
                "Chat successfully created",
                newChat
        );
    }

    public GetChatHistoryResponse getChatHistory(int chatId) {
        Chat chat = chatRepository.findChatByChatId(chatId);

        if (chat != null) {
            return new GetChatHistoryResponse(
                    true,
                    "Successfully fetched chat history",
                    chat.getMessages()
            );
        } else {
            return new GetChatHistoryResponse(
                    false,
                    "Chat does not exist",
                    null
            );
        }
    }
}

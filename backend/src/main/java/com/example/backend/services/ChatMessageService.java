package com.example.backend.services;

import com.example.backend.models.Chat;
import com.example.backend.models.Message;
import com.example.backend.repositories.ChatRepository;
import com.example.backend.responses.GetMessagesResponse;
import com.example.backend.responses.SendMessageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {
    private ChatRepository chatRepository;

    public ChatMessageService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public SendMessageResponse saveMessage(int chatId, Message message) {
        Chat chat = chatRepository.findChatById(chatId);

        if (chat != null) {
            List<Message> messages = chat.getMessages();
            messages.add(message);
            chat.setMessages(messages);
            chatRepository.save(chat);

            return new SendMessageResponse(
                    true,
                    "Successfully saved message",
                    message
            );
        } else {
            return new SendMessageResponse(
                    false,
                    "Failed to save message",
                    message
            );
        }
    }

    public GetMessagesResponse getMessages(int chatId) {
        Chat chat = chatRepository.findChatById(chatId);

        if (chat != null) {
            return new GetMessagesResponse(
                    true,
                    "Sucessfully fetched messages",
                    chat.getMessages()
            );
        } else {
            return new GetMessagesResponse(
                    false,
                    "Failed to fetch messages",
                    null
            );
        }
    }
}

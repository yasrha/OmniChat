package com.example.backend.repositories;

import com.example.backend.models.Chat;
import com.example.backend.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {
    Chat findChatByChatId(int chatId);
}
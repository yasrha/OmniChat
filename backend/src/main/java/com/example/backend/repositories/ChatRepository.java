package com.example.backend.repositories;

import com.example.backend.models.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String> {

}

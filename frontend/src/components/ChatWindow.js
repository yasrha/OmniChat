import React, { useState, useEffect } from "react";
import {
  connect,
  disconnect,
  sendMessage as sendWebSocketMessage,
} from "../config/WebsocketConfig";
import { useUser } from "../contexts/UserContext";
import MessageCard from "./MessageCard";

function ChatWindow(chatId) {
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState("");
  const { user } = useUser();

  useEffect(() => {
    const fetchMessages = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/chats/${chatId.chatId}/messages`
        );
        if (response.ok) {
          const apiResponse = await response.json();
          if (apiResponse && apiResponse.data) {
            setMessages(apiResponse.data); // Set messages if response is successful
          }
        } else {
          console.error("Error fetching messages:", response.status);
          // Handle error based on response status
        }
      } catch (error) {
        console.error("Error fetching messages:", error);
        // Handle network or parsing errors
      }
    };

    fetchMessages();

    const onMessageReceived = (newMessage) => {
      setMessages((prevMessages) => [...prevMessages, newMessage.body.data]);
    };

    connect(onMessageReceived, chatId.chatId); // Connect to WebSocket

    return () => {
      disconnect(); // Disconnect on component unmount
    };
  }, [chatId]);

  useEffect(() => {
    console.log("Updated messages:", messages);
  }, [messages]);

  const sendMessage = () => {
    if (message.trim() !== "") {
      const messageObj = { text: message, username: user.username };
      sendWebSocketMessage(chatId.chatId, messageObj);
      setMessage("");
    }
  };

  if (!chatId) {
    return <div className="chat-window">Select a chat to start messaging</div>;
  }

  return (
    <div className="chat-window">
      <div className="messages">
        {messages && messages.length > 0 ? (
          messages.map((msg, index) => (
            <MessageCard key={index} message={msg} />
          ))
        ) : (
          <div className="empty-chat-message">This chat is empty.</div>
        )}
      </div>
      <div className="message-input">
        <input
          type="text"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          placeholder="Type a message..."
        />
        <button onClick={sendMessage}>Send</button>
      </div>
    </div>
  );
}

export default ChatWindow;

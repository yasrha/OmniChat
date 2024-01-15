import React, { useState, useEffect } from "react";
import ChatList from "./ChatList";
import ChatWindow from "./ChatWindow";
import NewChatModal from "./NewChatModal";
import { useUser } from "../contexts/UserContext";
import "../App.css";
import { IconButton } from "@mui/material";
import AddCommentIcon from "@mui/icons-material/AddComment";

function ChatsPage() {
  const [chats, setChats] = useState([]);
  const { user } = useUser();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedChatId, setSelectedChatId] = useState(null);

  const handleChatSelect = (chatId) => {
    setSelectedChatId(chatId);
    console.log("chatId: ", chatId);
  };

  const handleNewChat = () => {
    setIsModalOpen(true);
  };

  const handleModalClose = () => {
    setIsModalOpen(false);
  };

  // Fetch the chats for a user
  useEffect(() => {
    const url = "http://localhost:8080/api/chats/fetchChats";
    console.log("user: ", user);

    const fetchData = async () => {
      try {
        const response = await fetch(url, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(user.chatIds),
        });

        const apiResponse = await response.json();

        if (response.ok) {
          if (apiResponse.data && apiResponse.data.length > 0) {
            // Case: User is in chats
            setChats(apiResponse.data);
          } else {
            // Case: User is in no chats
            setChats([]); // Set chats to an empty array
          }
        } else {
          // Case: There's an error
          console.error("HTTP Error:", response.status, apiResponse.message);
        }
      } catch (error) {
        console.error("Fetch error:", error);
      }
    };

    fetchData();
  }, [user]);

  // Create a new chat
  const handleCreateChat = async (usernames) => {
    // Retrieve the current logged-in user's username from the context
    const loggedInUsername = user && user.username;

    // Ensure that the logged-in user's username is included in the list
    // and prevent duplicates if the user already added their own username
    const usernamesForChat = loggedInUsername
      ? Array.from(new Set([loggedInUsername, ...usernames]))
      : [...usernames];

    const url = "http://localhost:8080/api/chats/create";
    try {
      const response = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ usernames: usernamesForChat }),
      });

      // Handle response
      if (response.ok) {
        // Handle successful response
        const data = await response.json();
        console.log("Chat created:", data.data);
        setChats((currentChats) => [...currentChats, data.data]);
      } else {
        // Handle HTTP errors
        console.error("HTTP Error:", response.status);
      }
    } catch (error) {
      console.error("Error creating chat:", error);
    }
  };

  return (
    <div className="chats-page">
      <div className="chat-list">
        <div className="chat-bar">
          <span>Chats</span>
          <IconButton onClick={handleNewChat}>
            <AddCommentIcon />
          </IconButton>
        </div>
        <ChatList chats={chats} onChatSelect={handleChatSelect} />
      </div>
      <div className="chat-window">
        {selectedChatId ? (
          <ChatWindow chatId={selectedChatId} />
        ) : (
          <div className="select-chat-message">
            Select a chat to view messages
          </div>
        )}
      </div>
      <NewChatModal
        open={isModalOpen}
        onClose={handleModalClose}
        onSubmit={handleCreateChat}
      />
    </div>
  );
}

export default ChatsPage;

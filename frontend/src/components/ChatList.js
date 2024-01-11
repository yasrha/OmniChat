import React from "react";
import "../App.css";

function ChatList({ chats }) {
  return (
    <div>
      {chats.map((chat) => (
        <div key={chat.id} className="chat-card">
          <div className="chat-info">
            <div className="chat-id">Chat ID: {chat.id}</div>
            <div className="chat-users">Users: {chat.usernames.join(", ")}</div>
          </div>
        </div>
      ))}
    </div>
  );
}

export default ChatList;

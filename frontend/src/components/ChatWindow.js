import React, { useState } from "react";

function ChatWindow() {
  const [message, setMessage] = useState("");

  const sendMessage = () => {
    console.log("Sending message:", message);
    // Logic to send the message goes here
    setMessage("");
  };

  return (
    <div className="chat-window">
      <h2>Chat Window</h2>
      {/* Placeholder for chat messages */}
      <div className="messages">{/* Messages will be displayed here */}</div>
      {/* Message input */}
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

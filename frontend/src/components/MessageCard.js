import React from "react";
import "../App.css";

function MessageCard({ message }) {
  return (
    <div className="message-card">
      <div className="message-sender">{message.username}</div>
      <div className="message-text">{message.text}</div>
    </div>
  );
}

export default MessageCard;

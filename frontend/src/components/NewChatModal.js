import React, { useState } from "react";
import { Modal, Button, TextField, IconButton } from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";

function NewChatModal({ open, onClose, onSubmit }) {
  const [usernames, setUsernames] = useState(["", ""]);

  const handleAddUser = () => {
    setUsernames([...usernames, ""]);
  };

  const handleChange = (index, value) => {
    const updated = [...usernames];
    updated[index] = value;
    setUsernames(updated);
  };

  const handleSubmit = () => {
    onSubmit(usernames.filter((username) => username.trim() !== ""));
    onClose();
  };

  return (
    <Modal open={open} onClose={onClose}>
      <div className="new-chat-modal">
        <div className="modal-header">
          <IconButton onClick={onClose}>
            <CloseIcon />
          </IconButton>
        </div>
        <div className="modal-body">
          {usernames.map((username, index) => (
            <TextField
              key={index}
              value={username}
              onChange={(e) => handleChange(index, e.target.value)}
              placeholder={`Username ${index + 1}`}
              style={{ padding: "5px", width: "100%" }}
            />
          ))}
        </div>
        <div className="modal-footer">
          <Button
            variant="contained"
            onClick={handleAddUser}
            style={{ marginRight: "10px" }}
          >
            Add User
          </Button>
          <Button variant="contained" onClick={handleSubmit}>
            Submit New Chat
          </Button>
        </div>
      </div>
    </Modal>
  );
}

export default NewChatModal;

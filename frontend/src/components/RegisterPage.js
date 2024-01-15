import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import ApiConfig from "../config/ApiConfig";
import Background from "../Background";

function RegisterPage() {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [usernameError, setUsernameError] = useState(false);
  const [emailError, setEmailError] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const textFieldStyle = {
    backgroundColor: "lightgray",
    color: "black",
    borderColor: "white",
    borderRadius: "5px",
  };

  const handleRegister = async () => {
    setUsernameError(false);
    setEmailError(false);
    const userDetails = {
      username,
      email,
      password,
    };

    const url = `${ApiConfig.baseUrl}${ApiConfig.endpoints.register}`;

    try {
      const response = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(userDetails),
      });

      const data = await response.json();

      if (response.status === 200) {
        console.log("Registration successful:", data);
        navigate("/chat");
      } else if (response.status === 409) {
        setErrorMessage(data.message);
        if (data.message.includes("email")) {
          setEmailError(true);
        }
        if (data.message.includes("username")) {
          setUsernameError(true);
        }
      } else {
        console.error(data.message);
      }
    } catch (error) {
      console.error("There was an error:", error);
    }
  };

  return (
    <div>
      <Box
        display="flex"
        flexDirection="column"
        alignItems="center"
        justifyContent="center"
        style={{ minHeight: "100vh" }}
      >
        <h1>Register new user!</h1>
        <Box marginBottom={2}>
          <TextField
            label="Username"
            variant="filled"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            style={textFieldStyle}
            error={usernameError}
            helperText={usernameError ? errorMessage : ""}
          />
        </Box>
        <Box marginBottom={2}>
          <TextField
            label="Email"
            variant="filled"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            style={textFieldStyle}
            error={emailError}
            helperText={emailError ? errorMessage : ""}
          />
        </Box>
        <Box marginBottom={2}>
          <TextField
            label="Password"
            type="password"
            variant="filled"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            style={textFieldStyle}
          />
        </Box>
        <Button variant="contained" color="primary" onClick={handleRegister}>
          Register
        </Button>
      </Box>
      <Background />
    </div>
  );
}

export default RegisterPage;

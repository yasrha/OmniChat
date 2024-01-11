import "./App.css";
import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import SplashScreen from "./components/SplashScreen";
import LoginPage from "./components/LoginPage";
import RegisterPage from "./components/RegisterPage";
import ChatsPage from "./components/ChatsPage";
import { UserProvider } from "./contexts/UserContext";
import Background from "./Background";

function App() {
  return (
    <UserProvider>
      <Router>
        <Routes>
          <Route exact path="/" element={<SplashScreen />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/chat" element={<ChatsPage />} />
        </Routes>
      </Router>
      <Background />
    </UserProvider>
  );
}

export default App;

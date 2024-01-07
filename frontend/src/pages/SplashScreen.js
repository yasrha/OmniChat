import React from 'react';
import { useNavigate } from 'react-router-dom';

function SplashScreen() {
    const navigate = useNavigate();

    const navigateToLogin = () => {
        navigate('/login');
    };

    const navigateToSignup = () => {
        navigate('/signup');
    };

    return (
        <div>
            <h1>Welcome to OmniChat</h1>
            <button onClick={navigateToLogin}>Login</button>
            <button onClick={navigateToSignup}>Register</button>
        </div>
    );
}

export default SplashScreen;

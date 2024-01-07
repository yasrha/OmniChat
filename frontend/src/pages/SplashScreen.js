import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '@mui/material';
import Background from '../Background';

function SplashScreen() {
    const navigate = useNavigate();

    const navigateToLogin = () => {
        navigate('/login');
    };

    const navigateToRegister = () => {
        navigate('/register');
    };

    return (
        <div className='splash-screen'>
            <h1>Welcome to OmniChat!</h1>
            <Button variant="contained" onClick={navigateToLogin}>Login</Button>
            <Button variant="contained" onClick={navigateToRegister}>Register</Button>
            <Background />
        </div>
    );
}

export default SplashScreen;

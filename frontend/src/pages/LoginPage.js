import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import ApiConfig from '../ApiConfig';
import Background from '../Background';

function LoginPage() {
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(false); 
    const [errorMessage, setErrorMessage] = useState('');

    const textFieldStyle = {
        backgroundColor: 'lightgray', 
        color: 'black',
        borderColor: 'white',
        borderRadius: '5px'
    };

    const handleLogin = async () => {
        setError(false); // Reset error state on new login attempt
        const loginDetails = {
            email,
            password
        };

        const url = `${ApiConfig.baseUrl}${ApiConfig.endpoints.login}`;

        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(loginDetails)
            });

            const data = await response.json();

            if (response.status === 200) {
                console.log('Login successful:', data);
                navigate('/chat');
            } else {
                setError(true);
                setErrorMessage('Invalid credentials');
            }
        } catch (error) {
            console.error('There was an error:', error);
            setError(true);
            setErrorMessage('An error occurred');
        }
    };

    return (
        <div>
            <Box
                display="flex"
                flexDirection="column"
                alignItems="center"
                justifyContent="center"
                style={{ minHeight: '100vh' }}
            >
                <Box marginBottom={2}>
                    <TextField
                        label="Email"
                        variant="filled"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        style={textFieldStyle}
                        InputLabelProps={{
                            style: { color: 'black' }
                        }}
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
                        InputLabelProps={{
                            style: { color: 'black' } 
                        }}
                        error={error}
                        helperText={error ? errorMessage : ''}
                    />
                </Box>
                <Button variant="contained" color="primary" onClick={handleLogin}>
                    Login
                </Button>
            </Box>
            <Background />
        </div>
    );
}

export default LoginPage;

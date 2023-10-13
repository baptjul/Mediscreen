import React, { useState } from 'react';
import fetchAPI from '../api/api';

export default function Connection() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [isLoginMode, setIsLoginMode] = useState(true);

    async function handleAuth() {
        const endpoint = isLoginMode ? '/usersService/login' : '/usersService/signup';
        try {
            const response = await fetchAPI(endpoint, 'POST', {
                username,
                password,
            });

            if (response) {
                localStorage.setItem('token', response);
                window.location.href = '/';
            } else {
                console.error('Authentication failed');
            }
        } catch (error: any) {
            console.error('Error during authentication:', error.message);
        }
    }

    function toggleMode() {
        setIsLoginMode(prevMode => !prevMode);  // Toggle the mode
    }

    return (
        <div className="container mt-5">
            <div className="card mx-auto" style={{ maxWidth: '40%' }}>
                <div className="card-body text-center">
                    <h2 className="card-title">{isLoginMode ? 'Login' : 'Sign Up'}</h2>  {/* Toggle title */}
                    <form>
                        <div className="mb-3">
                            <label htmlFor="username" className="form-label">
                                Username
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                id="username"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="password" className="form-label">
                                Password
                            </label>
                            <input
                                type="password"
                                className="form-control"
                                id="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </div>
                        <p className="mt-3 text-decoration-underline">
                            {isLoginMode ?
                                <span onClick={toggleMode} style={{ cursor: 'pointer', color: 'blue' }}>Create an account</span> :
                                <span onClick={toggleMode} style={{ cursor: 'pointer', color: 'blue' }}>Login</span>
                            }
                        </p>
                        <button
                            type="button"
                            className="btn btn-primary"
                            onClick={handleAuth}
                        >
                            {isLoginMode ? 'Login' : 'Sign Up'}  {/* Toggle button text */}
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
};
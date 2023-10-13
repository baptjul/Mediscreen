import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Routes, useLocation, Navigate, useNavigate} from 'react-router-dom';
import PatientList from './patients/patientList';
import MainNavbar from "./components/Navbar";
import AddPatient from "./patients/addPatient";
import Home from "./home/Home";
import {Helmet, HelmetProvider} from "react-helmet-async";
import Connection from "./connection/Connection";
import fetchAPI from "./api/api";


function TokenValidator() {
    const navigate = useNavigate();

    React.useEffect(() => {
        (async () => {
            await validateToken(navigate);
        })();
    }, [navigate]);

    return null;
}
async function validateToken(navigate: (to: string) => void) {
    const token = localStorage.getItem('token');
    if (token) {
        try {
            const checkToken = await fetchAPI<boolean>('/validateToken', 'POST', {token: token});
            if (!checkToken) {
                localStorage.removeItem('token');
                navigate('/connection');
            }
        } catch (error) {
            console.error('Token validation failed:', error);
            navigate('/connection');
        }
    } else {
        navigate('/connection');
    }
}

function App() {
    return (
        <HelmetProvider>
            <div className="App">
                <Helmet>
                    <title>MediScreen</title>
                    <link rel="icon" href="./img_icon.ico" />
                </Helmet>

                <Router>
                    <TokenValidator />
                    <ConditionalNavbar />
                    <Routes>
                        <Route path="/connection" element={<Connection />} />
                        <Route path="/" element={<Home />} />
                        <Route path="/patient/list" element={<PatientList />} />
                        <Route path="/patient/add" element={<AddPatient />} />
                    </Routes>
                </Router>
            </div>
        </HelmetProvider>
    );
}

function ConditionalNavbar() {
    const location = useLocation();
    if (location.pathname === '/connection') {
        return null;
    }

    return <MainNavbar />;
}

export default App;

import React from 'react';
import {Navbar, Nav, Image, Button} from 'react-bootstrap';
import {Link, useNavigate} from 'react-router-dom';
import logoImage from '../img.png';
import '../index.css';

export default function MainNavbar() {
    const navigate = useNavigate();

    const handleDisconnect = () => {
        localStorage.removeItem('token');
        navigate('/connection');
    };

    const isTokenStored = localStorage.getItem('token') !== null;

    return (
        <Navbar bg="dark" variant="dark" expand="lg">
            <Navbar.Brand as={Link} to="/">
                <Image src={logoImage} alt="Logo" width="30" height="30" className="mx-3"/>
                Mediscreen
            </Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav"/>
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="ml-auto">
                    <Nav.Link as={Link} to="/">Home</Nav.Link>
                    <Nav.Link as={Link} to="/patient/list">Patient List</Nav.Link>
                </Nav>
                {isTokenStored && (
                    <Button className="disconnectButton bg-danger btn-outline-dark" onClick={handleDisconnect}>
                        <i className="bi bi-power "></i>
                    </Button>
                )}
            </Navbar.Collapse>
        </Navbar>
    );
};

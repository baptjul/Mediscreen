import React from 'react';
import {Carousel} from 'react-bootstrap';
import icupatient from '../assets/icupatient-1709575448.jpeg';
import medical from '../assets/medical_hero_background.preview.jpg';
import notes from '../assets/P7130122-1.jpg';
import './home.css';


export default function Home() {
    return (
        <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <h1 className="title">Welcome to Mediscreen</h1>
            <div style={{width: '80%', maxWidth: '800px'}}>
                <Carousel>
                    <Carousel.Item>
                        <img
                            className="d-block w-100 carousel-image"
                            src={icupatient}
                            alt="First slide"
                        />
                    </Carousel.Item>
                    <Carousel.Item>
                        <img
                            className="d-block w-100 carousel-image"
                            src={medical}
                            alt="Second slide"
                        />
                    </Carousel.Item>
                    <Carousel.Item>
                        <img
                            className="d-block w-100 carousel-image"
                            src={notes}
                            alt="Third slide"
                        />
                    </Carousel.Item>
                </Carousel>
                <p className="text">Found all your patients, notes and data</p>
            </div>
        </div>
    );
}
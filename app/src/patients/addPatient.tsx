import React, { useState } from 'react';
import { Button, Form, Container } from 'react-bootstrap';
import PatientEntity from '../interfaces/PatientEntity';
import fetchAPI from "../api/api";

export default function AddPatient() {
    const initialPatient = {
        family: '',
        given: '',
        dob: '',
        sex: '',
        address: '',
        phone: ''
    };

    const [patient, setPatient] = useState<Omit<PatientEntity, 'id'>>(initialPatient);

    function handleChange(event: React.ChangeEvent<HTMLInputElement>) {
        const { name, value } = event.target;
        setPatient(prev => ({ ...prev, [name]: value }));
    };

    async function handleSubmit(event: React.FormEvent) {
        event.preventDefault();
        try {
            await fetchAPI('/patientService/patient/add', 'POST', patient);
            alert("Patient added successfully!");
            setPatient(initialPatient);
        } catch (error: any) {
            alert("Failed to add patient: " + error.message);
        }
    }

    const isAddButtonDisabled = !patient.family || !patient.given;

    return (
        <Container>
            <h2 className="mt-3 mb-3">Add a Patient</h2>
            <Form onSubmit={handleSubmit}>
                <Form.Group controlId="family">
                    <Form.Label>Family Name</Form.Label>
                    <Form.Control type="text" name="family" value={patient.family} onChange={handleChange} />
                </Form.Group>

                <Form.Group controlId="given">
                    <Form.Label>Given Name</Form.Label>
                    <Form.Control type="text" name="given" value={patient.given} onChange={handleChange} />
                </Form.Group>

                <Form.Group controlId="dob">
                    <Form.Label>Date of Birth</Form.Label>
                    <Form.Control type="date" name="dob" value={patient.dob} onChange={handleChange} />
                </Form.Group>

                <Form.Group controlId="sex">
                    <Form.Label>Sex</Form.Label>
                    <Form.Control as="select" name="sex" value={patient.sex} onChange={handleChange}>
                        <option value="male">Male</option>
                        <option value="female">Female</option>
                    </Form.Control>
                </Form.Group>

                <Form.Group controlId="address">
                    <Form.Label>Address</Form.Label>
                    <Form.Control type="text" name="address" value={patient.address} onChange={handleChange} />
                </Form.Group>

                <Form.Group controlId="phone">
                    <Form.Label>Phone</Form.Label>
                    <Form.Control type="text" name="phone" value={patient.phone} onChange={handleChange} />
                </Form.Group>

                <Button className="mt-3" variant="primary" type="submit" disabled={isAddButtonDisabled}>
                    Add Patient
                </Button>
            </Form>
        </Container>
    );
}

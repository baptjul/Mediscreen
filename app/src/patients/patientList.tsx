import React, {useEffect, useState} from 'react';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import fetchAPI from '../api/api';
import PatientEntity from '../interfaces/PatientEntity';
import 'bootstrap/dist/css/bootstrap.min.css';
import './patientList.css';
import {Link} from 'react-router-dom';
import PatientInfo from "../components/PatientInfo";
import PatientHistory from "../components/PatientHistory";

export default function PatientList() {
    const [patients, setPatients] = useState<PatientEntity[]>([]);
    const [expandedPatient, setExpandedPatient] = useState<PatientEntity | null>(null);
    const [search, setSearch] = useState('');
    const [editMode, setEditMode] = useState<number | null>(null);
    const [historyEditMode, setHistoryEditMode] = useState<number | null>(null);
    const [editedhistorys, setEditedhistorys] = useState<{ [key: number]: PatientEntity }>({});
    const [editedPatients, setEditedPatients] = useState<{ [key: number]: PatientEntity }>({})

    useEffect(() => {
        fetchPatients();
    }, []);

    async function fetchPatients() {
        try {
            const data: any = await fetchAPI<PatientEntity[]>('/patientService/patient/list', 'GET');

            const enhancedDataPromises = data.map(async (patient: any) => {
                let historyData, assessmentData;

                try {
                    historyData = await fetchAPI(`/historyService/patHistory/patient/${patient.id}`, 'GET');
                } catch (error) {
                    console.error('Error fetching history data:', error);
                }

                try {
                    assessmentData = await fetchAPI(`/assessService/assess/${patient.id}`, 'GET');
                } catch (error) {
                    console.error('Error fetching assessment data:', error);
                }
                return {
                    ...patient,
                    history: historyData,
                    assessment: assessmentData,
                };
            });

            const enhancedData = await Promise.all(enhancedDataPromises);
            setPatients(enhancedData);
        } catch (error) {
            console.error('Error fetching patients:', error);
        }
    }

    async function fetchAndUpdateAssessment(patient: PatientEntity) {
        const assessmentData: any = await fetchAPI(`/assessService/assess/${patient.id}`, 'GET');
        setPatients(prevPatients => {
            return prevPatients.map(prevPatient => {
                if (prevPatient.id === patient.id) {
                    return {
                        ...prevPatient,
                        assessment: assessmentData,
                    };
                } else {
                    return prevPatient;
                }
            });
        })
    }

    function handleExpandClick(patient: PatientEntity) {
        if (expandedPatient === patient) {
            setExpandedPatient(null);
            setEditMode(null);
            setHistoryEditMode(null);
        } else {
            setExpandedPatient(patient);
            setEditMode(null);
            setHistoryEditMode(null);
        }
    }

    function handleSearchChange(event: React.ChangeEvent<HTMLInputElement>) {
        setSearch(event.target.value);
    }

    function handleInputChange(event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>, patientId: number, field: string) {
        const updatedPatient = {...editedPatients[patientId], [field]: event.target.value};
        setEditedPatients(prevEditedPatients => ({
            ...prevEditedPatients,
            [patientId]: updatedPatient,
        }));
    }

    const toggleEditMode = (patient: PatientEntity) => {
        if (editMode === patient.id) {
            setEditMode(null);
            setHistoryEditMode(null);
        } else {
            setEditMode(patient.id);
            if (!editedPatients[patient.id]) {
                setEditedPatients(prevEditedPatients => ({
                    ...prevEditedPatients,
                    [patient.id]: patient,
                }));
            }
        }
    };

    const toggleHistoryEditMode = (patient: PatientEntity) => {
        if (historyEditMode === patient.id) {
            setHistoryEditMode(null);
        } else {
            setHistoryEditMode(patient.id);
            if (!editedhistorys[patient.id]) {
                setEditedhistorys(prevEditedPatients => ({
                    ...prevEditedPatients,
                    [patient.id]: patient,
                }));
            }
        }
    }

    const filteredPatients = patients.filter(
        patient =>
            patient.given.toLowerCase().includes(search.toLowerCase()) ||
            patient.family.toLowerCase().includes(search.toLowerCase())
    );

    async function onDelete(patientId: number) {
        try {
            await fetchAPI(`/patientService/patient/delete/${patientId}`, 'DELETE');
            const updatedPatients = patients.filter(patient => patient.id !== patientId);
            setPatients(updatedPatients);

            setExpandedPatient(null);
            setEditMode(null);
            setHistoryEditMode(null);
        } catch (error) {
            console.error('Error deleting patient:', error);
        }
    }

    async function onSave(patientId: number) {
        try {
            const updatedPatient = editedPatients[patientId];
            if (updatedPatient) {
                const updatedPatientData: any = await fetchAPI(`/patientService/patient/update/${patientId}`, 'PUT', updatedPatient);

                const updatedPatientsList = patients.map(patient => {
                    if (patient.id === patientId) {
                        return {
                            ...patient,
                            ...updatedPatientData,
                        };
                    } else {
                        return patient;
                    }
                });

                setPatients(updatedPatientsList);

                const updatedPatientInList = updatedPatientsList.find(patient => patient.id === patientId);
                if (updatedPatientInList) {
                    setExpandedPatient(updatedPatientInList);
                }

                setEditMode(null);
                setHistoryEditMode(null);
            }
        } catch (error) {
            console.error('Error updating patient:', error);
        }
    }

    return (
        <div>
            <div className="position-relative mx-3">
                <h1>Patient List</h1>

                <Link to="/patient/add">
                    <Button className="position-absolute top-0 end-0 mt-3 me-3" variant="primary">
                        add patient
                    </Button>
                </Link>
            </div>
            <div className="content mt-4">
                <div className="mx-5 my-3">
                    <Form.Group controlId="searchForm">
                        <Form.Control
                            type="text"
                            placeholder="Search by name"
                            value={search}
                            onChange={handleSearchChange}
                        />
                    </Form.Group>
                </div>

                {filteredPatients.length === 0 ? (
                    <p>No patients found !</p>
                ) : (
                    <div className="row mt-3">
                        {filteredPatients.map(patient => (
                            <div
                                key={patient.id}
                                className={`col-12 mb-1 ${expandedPatient === patient ? 'selected' : ''}`}
                            >
                                <Card className={"text-white bg-dark border-info mx-2"}>
                                    <Card.Body>
                                        <Card.Title style={{fontWeight: "bold"}}>
                                            {editMode === patient.id ? (
                                                <>
                                                    <input
                                                        type="text"
                                                        value={editedPatients[patient.id]?.given}
                                                        onChange={e => handleInputChange(e, patient.id, 'given')}
                                                    />
                                                    <input
                                                        type="text"
                                                        value={editedPatients[patient.id]?.family}
                                                        onChange={e => handleInputChange(e, patient.id, 'family')}
                                                    />
                                                </>
                                            ) : (
                                                <>
                                                    {patient.given} {patient.family}
                                                </>
                                            )}
                                        </Card.Title>
                                        {expandedPatient === patient && (
                                            <div className="patientDetails">
                                                <div className="patientColumn">
                                                    <PatientInfo
                                                        patient={patient}
                                                        editMode={editMode}
                                                        editedPatients={editedPatients}
                                                        handleInputChange={handleInputChange}/>
                                                    <div className="buttonDetails">
                                                        {expandedPatient === patient && (
                                                            <div>
                                                                {editMode === patient.id ? (
                                                                    <Button
                                                                        className={'mt-3 m-2'}
                                                                        variant="success"
                                                                        onClick={() => onSave(patient.id)}
                                                                    >
                                                                        <i className="bi bi-save"> Save</i>
                                                                    </Button>
                                                                ) : (
                                                                    <Button
                                                                        className={'mt-3 m-2'}
                                                                        variant="success"
                                                                        onClick={() => toggleEditMode(patient)}
                                                                    >
                                                                        <i className="bi bi-pen-fill"> Edit</i>
                                                                    </Button>
                                                                )}

                                                                {editMode === patient.id && (
                                                                    <Button onClick={() => toggleEditMode(patient)}
                                                                            className={"mt-3 m-2"} variant="danger">
                                                                        <i className="bi bi-x"> Cancel</i>
                                                                    </Button>
                                                                )}
                                                            </div>
                                                        )}
                                                    </div>
                                                </div>
                                                <div className="patientColumn">
                                                    <h4>Assessment :</h4>
                                                    <p>{patient.assessment}</p>
                                                </div>
                                                <div className="patientColumn">
                                                    <PatientHistory patientId={patient.id} histories={patient.history}
                                                                    editMode={historyEditMode}
                                                                    updateAssess={() => fetchAndUpdateAssessment(patient)}/>
                                                    <div className="buttonDetails">
                                                        <Button className="historyButtons"
                                                                onClick={() => toggleHistoryEditMode(patient)}>
                                                            {historyEditMode ? 'Exit Edit Mode' : 'Enter Edit Mode'}
                                                        </Button>
                                                    </div>
                                                </div>
                                            </div>
                                        )}
                                        <div>
                                            <Button
                                                className={'mt-3 m-2'}
                                                variant="primary"
                                                onClick={() => handleExpandClick(patient)}
                                            >
                                                {expandedPatient === patient ?
                                                    <i className="bi bi-arrow-bar-up"> close</i> :
                                                    <i className="bi bi-arrow-bar-down"> view</i>}
                                            </Button>
                                            <Button onClick={() => onDelete(patient.id)} className={"mt-3 m-2"}
                                                    variant="danger">
                                                <i className="bi bi-trash"> Delete</i>
                                            </Button>
                                        </div>

                                    </Card.Body>
                                </Card>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}

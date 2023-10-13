import React from 'react';
import PatientEntity from "../interfaces/PatientEntity";
import Card from 'react-bootstrap/Card';
import '../patients/patientList.css';

interface PatientInfoProps {
    patient: PatientEntity;
    editMode: number | null;
    editedPatients: { [key: number]: PatientEntity };
    handleInputChange: (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>, patientId: number, field: string) => void;
}

const PatientInfo: React.FC<PatientInfoProps> = ({
                                                     patient,
                                                     editMode,
                                                     editedPatients,
                                                     handleInputChange,
                                                 }) => {
    return (
        <div className="patientInfo">
            <Card.Text style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
                <span style={{fontWeight: 'bold'}}>Birth Date :</span>
                {editMode === patient.id ? (
                    <input
                        type="date"
                        value={editedPatients[patient.id]?.dob}
                        onChange={(e) => handleInputChange(e, patient.id, 'dob')}
                    />
                ) : (
                    patient.dob
                )}
            </Card.Text>

            <Card.Text style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
                <span style={{fontWeight: 'bold'}}>Gender :</span>
                {editMode === patient.id ? (
                    <select
                        value={editedPatients[patient.id]?.sex}
                        onChange={(e) => handleInputChange(e, patient.id, 'sex')}
                    >
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                    </select>
                ) : (
                    patient.sex
                )}
            </Card.Text>

            <Card.Text style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
                <span style={{fontWeight: 'bold'}}>Address :</span>
                {editMode === patient.id ? (
                    <input
                        type="text"
                        value={editedPatients[patient.id]?.address}
                        onChange={(e) => handleInputChange(e, patient.id, 'address')}
                    />
                ) : (
                    patient.address
                )}
            </Card.Text>

            <Card.Text style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
                <span style={{fontWeight: 'bold'}}>Phone :</span>
                {editMode === patient.id ? (
                    <input
                        type="tel"
                        value={editedPatients[patient.id]?.phone}
                        onChange={(e) => handleInputChange(e, patient.id, 'phone')}
                    />
                ) : (
                    patient.phone
                )}
            </Card.Text>
        </div>
    );
};

export default PatientInfo;
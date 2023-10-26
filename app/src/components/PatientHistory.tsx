import React, {useEffect, useState} from 'react';
import '../patients/patientList.css';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import HistoryEntity from "../interfaces/HistoryEntity";
import fetchAPI from "../api/api";

interface PatientHistoryProps {
    patientId: number;
    histories: HistoryEntity[] | undefined;
    editMode: number | null;
    updateAssess: () => void;
}

const PatientHistory: React.FC<PatientHistoryProps> = ({patientId, histories, editMode, updateAssess}) => {
    const [editedHistoryNotes, setEditedHistoryNotes] = useState<{ [key: number]: string }>({});
    const [usedHistory, setUsedHistory] = useState<any[] | undefined>(histories);


    function historyInputChange(historyId: string, event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) {
        const updatedNotes = {...editedHistoryNotes};
        updatedNotes[Number(historyId)] = event.target.value;
        setEditedHistoryNotes(updatedNotes);
    }

    async function onSave(historyId: string) {
        if (!usedHistory) return;
        const updatedNote = editedHistoryNotes[Number(historyId)];
        if (updatedNote && usedHistory) {
            const updatedHistory = usedHistory.find(e => e.id === historyId);
            if (updatedHistory) {
                updatedHistory.note = updatedNote;
            }
        }

        try {
            if (!usedHistory.find(e => e.id === historyId)?.new) {
                await fetchAPI(`/historyService/patHistory/update/${historyId}`, 'PUT', usedHistory.find(e => e.id === historyId));
                alert("Note updated !");
            } else {
                delete usedHistory.find(e => e.id === historyId)?.new;
                await fetchAPI(`/historyService/patHistory/add`, 'POST', usedHistory.find(e => e.id === historyId));
                alert("New note added !");
            }

            const updatedNotes = {...editedHistoryNotes};
            delete updatedNotes[Number(historyId)];
            setEditedHistoryNotes(updatedNotes);
            updateAssess();
        } catch (error) {
            console.error('Error updating history note:', error);
        }
    }

    async function onDelete(historyId: string) {
        if (!usedHistory) return;
        try {
            if (!usedHistory.find(e => e.id === historyId)?.new) {
                await fetchAPI(`/historyService/patHistory/delete/${historyId}`, 'DELETE');
            }

            const updatedHistories = usedHistory.filter(e => e.id !== historyId);
            setUsedHistory(updatedHistories);
            setEditedHistoryNotes({...editedHistoryNotes});
            updateAssess();
        } catch (error) {
            console.error('Error deleting history note:', error);
        }
    }

    async function onAddNote() {
        try {
            const addedHistoryNote: HistoryEntity = {id: Math.random().toString(), patId: patientId, note: ''};
            const newHistoryNote = {...addedHistoryNote, new: true};

            setUsedHistory([...(usedHistory || []), newHistoryNote]);
        } catch (error) {
            console.error('Error adding history note:', error);
        }
    }

    function reset(historyId: string) {
        const updatedNotes = {...editedHistoryNotes};
        delete updatedNotes[Number(historyId)];
        setEditedHistoryNotes(updatedNotes);
    }

    return (
        <div className="patientHistory">
            <div className="scrollableContent">
                {usedHistory && usedHistory.map((history, historyIndex) => (
                    <div key={history.id}>
                        {editMode ? (
                            <div>
                                <Form.Control
                                    as="textarea"
                                    rows={5}
                                    value={editedHistoryNotes[Number(history.id)] || history.note}
                                    onChange={(e) => historyInputChange(history.id, e)}
                                />
                                <Button className="historyButtons" onClick={() => onSave(history.id)}><i
                                    className="bi bi-save"> Save</i></Button>
                                <Button className="historyButtons" onClick={() => reset(history.id)}><i
                                    className="bi bi-arrow-counterclockwise"></i></Button>
                                <Button className="historyButtons" variant="danger"
                                        onClick={() => onDelete(history.id)}><i
                                    className="bi bi-trash"></i></Button>
                            </div>
                        ) : (
                            <div>
                                <p>{history.note}</p>
                            </div>
                        )}
                        <div className="divider"/>
                    </div>
                ))}
                {editMode && (
                    <div>
                        <Button className="historyButtons" onClick={onAddNote}><i className="bi bi-plus"></i> Add
                            Note</Button>
                    </div>
                )}
            </div>
        </div>
    );
};

export default PatientHistory;
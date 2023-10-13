import HistoryEntity from "./HistoryEntity";

export default interface PatientEntity {
    id: number;
    family: string;
    given: string;
    dob: string;
    sex: string;
    address: string;
    phone: string;
    history?: HistoryEntity[];
    assessment?: string;
}

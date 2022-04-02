import { Document } from '../models/user/document';
import { Phone } from '../models/user/phone';

export class ResponseLoginDTO {

    id: number;
    
    dateOfBirth: string;

    firtsName: string;

    lastName: string;

    email: string;

    password: string;

    documents: Document[];

    phones: Phone[];
}

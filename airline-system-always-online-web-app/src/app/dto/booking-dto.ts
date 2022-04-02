import { Traveler } from '../models/traveler';

export class BookingDTO {

    traveler_id: number;

    document_id: number;

    phone_id: number;

    selectedPosition: number;

    originLocationCode: string;

    destinationLocationCode: string;

    departureDate: string;

    returnDate: string;
    
    adults: number;

    children: number;

    totalPrice: number;

    firstFlightAirline: string;

    travelers: Traveler[];
}

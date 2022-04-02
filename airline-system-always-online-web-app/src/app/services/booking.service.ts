import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  private baseURL = 'http://169.63.40.61/bookings-dev/bookings';

  constructor(private http: HttpClient) { }

  getBookingById(id: number): Observable<any> {
    return this.http.get(`${this.baseURL}/${id}`);
  }

  getBookingByTravelerId(id: number): Observable<any> {
    return this.http.get(`${this.baseURL}/travelers/${id}`);
  }

  getBookingList():Observable<any> {
    return this.http.get(`${this.baseURL}`);
  }

  saveBooking(booking: Object) : Observable<any> {
    return this.http.post(`${this.baseURL}`, booking);
  }

  updateBooking(booking: Object) : Observable<any> {
    return this.http.put(`${this.baseURL}`, booking);
  }

  deleteBookingById(id: number): Observable<any> {
    return this.http.delete(`${this.baseURL}/${id}`);
  }

  deleteAllBookings(): Observable<any> {
    return this.http.delete(`${this.baseURL}`);
  }
}

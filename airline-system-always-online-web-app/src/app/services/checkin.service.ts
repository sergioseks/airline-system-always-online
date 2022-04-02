import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CheckinService {

  private baseURL = 'http://169.63.40.61/checkins-dev/checkins';

  constructor(private http: HttpClient) { }

  getCheckinById(id: number): Observable<any> {
    return this.http.get(`${this.baseURL}/${id}`);
  }

  getCheckinByTravelerId(id: number): Observable<any> {
    return this.http.get(`${this.baseURL}/travelers/${id}`);
  }

  getCheckinList():Observable<any> {
    return this.http.get(`${this.baseURL}`);
  }

  saveCheckin(booking: Object) : Observable<any> {
    return this.http.post(`${this.baseURL}`, booking);
  }

  updateCheckin(booking: Object) : Observable<any> {
    return this.http.put(`${this.baseURL}`, booking);
  }

  deleteCheckinById(id: number): Observable<any> {
    return this.http.delete(`${this.baseURL}/${id}`);
  }

  deleteAllCheckins(): Observable<any> {
    return this.http.delete(`${this.baseURL}`);
  }
}

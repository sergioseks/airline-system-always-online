import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseURL = 'http://169.63.40.61/users-dev/users';

  constructor(private http: HttpClient) { }

  login(user: Object) {
    return this.http.post(`${this.baseURL}/login`, user);
  }

  getUserById(id: number): Observable<any> {
    return this.http.get(`${this.baseURL}/${id}`);
  }

  getUserList():Observable<any> {
    return this.http.get(`${this.baseURL}`);
  }

  saveUser(user: Object) : Observable<any> {
    return this.http.post(`${this.baseURL}`, user);
  }

  updateUser(user: Object) : Observable<any> {
    return this.http.put(`${this.baseURL}`, user);
  }

  deleteUserById(id: number): Observable<any> {
    return this.http.delete(`${this.baseURL}/${id}`);
  }

  deleteAllUser(): Observable<any> {
    return this.http.delete(`${this.baseURL}`);
  }

}

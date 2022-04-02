import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http'
import { Observable } from 'rxjs';
import { ListDTO } from '../dto/list-dto';

@Injectable({
  providedIn: 'root'
})
export class ListService {

  private baseURL = 'http://169.63.40.61/list-dev/flights';

  constructor(private http: HttpClient) { }

  getListById(id: string): Observable<any> {
    return this.http.get(`${this.baseURL}/${id}`);
  }

  getList(listDTO: ListDTO):Observable<any>{
    return this.http.post(`${this.baseURL}`, listDTO);
  }
}

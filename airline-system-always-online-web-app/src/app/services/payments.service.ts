import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {HttpClient} from '@angular/common/http'
import { PaymentIntentDTO } from '../dto/payment-intent-dto';

@Injectable({
  providedIn: 'root'
})
export class PaymentsService {

  private baseURL = 'http://169.63.40.61/payments-dev/payments';

  constructor(private http: HttpClient) { }

  buy(paymentIntentDTO: PaymentIntentDTO): Observable<any> {
    return this.http.post(`${this.baseURL}/create`, paymentIntentDTO);
  }

  confirm(id: string): Observable<any> {
    return this.http.post(`${this.baseURL}/confirm/${id}`, {});
  }

  cancel(id: string):Observable<any>{
    return this.http.post(`${this.baseURL}/cancel/${id}`, {});
  }
}

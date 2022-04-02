import { Component } from '@angular/core';
import { ResponseLoginDTO } from './dto/response-login-dto';
import { Booking } from './models/booking';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'Airline System Always Online';

  selection: any = 'flights';

  userLogin: ResponseLoginDTO;

  selectedFlight: number;

  selectedBooking: Booking;

  setSelection(key: string) {
    this.selection = key;
    console.log(this.selection);
  }

 }

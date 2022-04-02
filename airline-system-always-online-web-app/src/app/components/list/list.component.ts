import { Component, OnInit, EventEmitter, Output, Inject, Input } from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { List } from '../../models/list';
import { ListService } from '../../services/list.service';
import {PageEvent} from '@angular/material/paginator';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {Overlay} from '@angular/cdk/overlay';
import { BookingService } from '../../services/booking.service';
import { Booking } from '../../models/booking';
import { BookingDTO } from '../../dto/booking-dto';
import { ListDTO } from '../../dto/list-dto';
import { Traveler } from '../../models/traveler';
import { Observable } from 'rxjs';
import { ResponseLoginDTO } from '../../dto/response-login-dto';

export interface DialogData {
  changeBooking: EventEmitter<string>;
  confirm: boolean;
}

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  list: List[];

  booking: Booking = new Booking();
  
  listDTO: ListDTO = new ListDTO();

  bookingDTO: BookingDTO = new BookingDTO();
  
  travelers: Traveler[] = [{
		"dateOfBirth": "1994-08-30",
		"firtsName": "Sergio",
		"lastName": "Espinal"
	}];

  public viewMicroserviceList: boolean = false;

  id: string;

  public loading: boolean = false;

  refresh : boolean = false;

  viewPanel: boolean = false;

  viewError: boolean = false;

  deploymentVersion: string;

  pagedList = []

  breakpoint: number = 3;//to adjust to screen

  // MatPaginator Inputs
  length: number = 0;

  pageSize: number = 1;//displaying three cards each row

  pageSizeOptions: number[] = [5, 10, 15, 20];

  // comunication to booking
  @Output()
  positionSelected: EventEmitter<number> = new EventEmitter<number>();

  @Output()
  changeBooking: EventEmitter<string> = new EventEmitter<string>();

  // receive user login
  @Input()
  userLogin: ResponseLoginDTO;

  // selectionFlight: any = 0;

  constructor(public dialog: MatDialog, public overlay: Overlay, private listService: ListService, private bookingService: BookingService) { }

  ngOnInit(): void {

    console.log('User login from flights', this.userLogin);

  }

  bookignFlight(flight: List): Observable<any> {

    console.log("out if");

    if (flight != undefined && this.userLogin != undefined) {

      this.bookingDTO.traveler_id = this.userLogin.id;
      this.bookingDTO.document_id = this.userLogin.documents[0].id;
      this.bookingDTO.phone_id = this.userLogin.phones[0].id;
      this.bookingDTO.originLocationCode = this.searchFlightForm.value.from;
      this.bookingDTO.destinationLocationCode = this.searchFlightForm.value.to;
      this.bookingDTO.departureDate = this.formatDate(this.searchFlightForm.value.departureDate);
      this.bookingDTO.returnDate = this.formatDate(this.searchFlightForm.value.returnDate);
      this.bookingDTO.adults = 1;
      this.bookingDTO.children = 0;
      this.bookingDTO.totalPrice = Number(flight.price);
      this.bookingDTO.travelers = this.travelers;

      console.log('Traveler id: ', this.bookingDTO.traveler_id);
      console.log('Document id: ', this.bookingDTO.document_id);

      return this.bookingService.saveBooking(this.bookingDTO);

    }

  }

  formatDate(date: Date) {

    var d = new Date(date),

    month = '' + (d.getMonth() + 1),

    day = '' + d.getDate(),

    year = d.getFullYear();
  
    if (month.length < 2) month = '0' + month;

    if (day.length < 2) day = '0' + day;
  
    return [year, month, day].join('-');
}

  //setSelection(key: number) {

    //this.selectionFlight = key;

    //this.positionSelected.emit(key);

    //this.openDialog();

    //console.log(this.selectionFlight);

  //}

  getPagination() {

    this.pagedList = this.list.slice(0, 5);

    this.length = this.list.length;

  }

  OnPageChange(event: PageEvent){

    let startIndex = event.pageIndex * event.pageSize;

    let endIndex = startIndex + event.pageSize;

    if(endIndex > this.length){

      endIndex = this.length;

    }

    this.pagedList = this.list.slice(startIndex, endIndex);
  }

  searchFlightForm = new FormGroup({

    from : new FormControl(),

    to : new FormControl(),

    departureDate: new FormControl(),

    returnDate: new FormControl()

  });

  get from() {
    return this.searchFlightForm.get('from');
  }

  get to() {
    return this.searchFlightForm.get('to');
  }

  get departureDate() {
    return this.searchFlightForm.get('departureDate');
  }

  get returnDate() {
    return this.searchFlightForm.get('returnDate');
  }

  searchFlight() {

    try {

      this.listDTO.originLocationCode = this.searchFlightForm.value.from;
      this.listDTO.destinationLocationCode = this.searchFlightForm.value.to;
      this.listDTO.departureDate = this.formatDate(this.searchFlightForm.value.departureDate);
      this.listDTO.returnDate = this.formatDate(this.searchFlightForm.value.returnDate);
      this.listDTO.adults = 1;

      this.listService.getList(this.listDTO).toPromise().then(

        res => {

          console.log('res: ' + JSON.stringify(res));

          if(res['errorCode'] === '0'){

            this.list = res['data'];

            this.loading = false;

            this.viewError = false;

            this.getPagination();

          }else {
            this.viewError = true;
          }
        }, error => {
            console.error('error: ' + error);

            this.loading = false;

            this.viewError = true;
        }
      );  
    } catch (error) {
      this.viewError = true;
    }
  }

  confirm: boolean;

  openDialog(flight: List): void {
    const dialogRef = this.dialog.open(BookingDialog, {
      width: '400px',
      scrollStrategy: this.overlay.scrollStrategies.noop(),
      data: { confirm: this.confirm }
    });

    dialogRef.afterClosed().subscribe(result => {

      this.confirm = result;

      if (this.confirm) {

        console.log('Print flight: ', flight);

        this.bookignFlight(flight).
        toPromise().then( data => { if (data != undefined) console.log('Data from booking', data); this.changeBooking.emit('booking');});
      }
      
      console.log('The dialog was closed: ' + result);
    });
  }

}

@Component({
  selector: '../../components/booking/booking-dialog',
  templateUrl: '../../components/booking/booking-dialog.html',
  styleUrls: ['../../components/booking/booking-dialog.css']
})
export class BookingDialog {

  constructor(
    public dialogRef: MatDialogRef<BookingDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  onConfirm(): void {
    this.dialogRef.close(true);
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }

}
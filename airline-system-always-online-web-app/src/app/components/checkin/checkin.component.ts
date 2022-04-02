import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Booking } from '../../models/booking';
import { CheckinService } from '../../services/checkin.service'
import { FormGroup, FormControl } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { Checkin } from '../../models/checkin';

@Component({
  selector: 'app-checkin',
  templateUrl: './checkin.component.html',
  styleUrls: ['./checkin.component.css']
})
export class CheckinComponent implements OnInit {

  @Input()
  selectedBooking: Booking;

  list: Checkin[];

  checkin: Checkin = new Checkin();

  public viewMicroserviceCheckin: boolean = false;

  public loading: boolean = false;

  refresh: boolean = false;

  viewPanel: boolean = false;

  viewError: boolean = false;

  deploymentVersion: string;

  pagedList = [];

  breakpoint: number = 3; // to adjust to screen

  // MatPaginator Inputs
  length: number = 0;

  pageSize: number = 1; // displaying three cards each row

  pageSizeOptions: number[] = [5, 10, 15, 20];

  constructor(private checkinService: CheckinService) { }

  ngOnInit(): void {

    this.getCheckinList();

  }

  checkinForm = new FormGroup({

    to : new FormControl(),

    dnumber: new FormControl(),

    departureDate: new FormControl(),

  });

  get to() {
    return this.checkinForm.get('to');
  }

  get dnumber() {
    return this.checkinForm.get('dnumber');
  }

  get departureDate() {
    return this.checkinForm.get('departureDate');
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

  sendCheckin() {
    try {

      this.checkin.travelerId = 3;
      this.checkin.bookingId = 5;
      this.checkin.destination = this.checkinForm.value.to;
      this.checkin.departureDate = this.formatDate(this.checkinForm.value.departureDate);
      this.checkin.documentNumber = this.checkinForm.value.dnumber;

      this.checkinService.saveCheckin(this.checkin).toPromise().then(

        res => {

          console.log('res: ' + JSON.stringify(res));

          if(res['errorCode'] === 0){

            //this.list = res['data'];

            this.loading = false;

            this.viewError = false;

            //this.saveCheckin.emit('checkin');

            this.getCheckinList();

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

  getPagination() {

    this.list = this.list.slice().reverse();

    this.pagedList = this.list.slice(0, 5);

    this.length = this.list.length;

  }

  OnPageChange(event: PageEvent) {

    let startIndex = event.pageIndex * event.pageSize;

    let endIndex = startIndex + event.pageSize;

    if (endIndex > this.length) {

      endIndex = this.length;

    }

    this.pagedList = this.list.slice(startIndex, endIndex);
  }

  getCheckinList() {

    try {

      this.checkinService.getCheckinList().toPromise().then(

        rest => {

          console.log('res: ' + JSON.stringify(rest));

          if (rest['errorCode'] === 0) {

            this.list = rest['data'];

            this.loading = false;

            this.viewError = false;

            this.getPagination();

          } else {

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

}

import { Component, OnInit, Input, Output, EventEmitter, Inject } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Overlay } from '@angular/cdk/overlay';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Booking } from '../../models/booking';
import { BookingService } from '../../services/booking.service';

export interface DialogData {

  confirm: boolean;

  booking: Booking;

}

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent implements OnInit {

  list: Booking[];

  public viewMicroserviceBooking: boolean = false;

  id: string;

  public loading: boolean = false;

  refresh: boolean = false;

  viewPanel: boolean = false;

  public viewError: boolean = false;

  deploymentVersion: string;

  pagedList = []

  breakpoint: number = 3; // to adjust to screen

  // MatPaginator Inputs
  length: number = 0;

  pageSize: number = 1; // displaying three cards each row

  pageSizeOptions: number[] = [5, 10, 15, 20];

  callInit: boolean;

  @Output()
  changePayment: EventEmitter<string> = new EventEmitter<string>();

  @Output()
  selectedBooking: EventEmitter<Booking> = new EventEmitter<Booking>();

  constructor(public dialog: MatDialog, public overlay: Overlay, private bookingService: BookingService) { }

  ngOnInit(): void {

    this.callInit = true;

    this.getBookingList();

  }

  getPagination() {

    if(this.callInit) {

      this.callInit = false;

      this.list = this.list.slice().reverse();

    }

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

  getBookingList() {

    try {

      this.bookingService.getBookingList().toPromise().then(

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

  // dialog
  confirm: boolean;

  openDialog(booking: Booking): void {

    const dialogRef = this.dialog.open(ListDialog, {

      width: '400px',

      scrollStrategy: this.overlay.scrollStrategies.noop(),

      data: { confirm: this.confirm, booking: booking }

    });

    dialogRef.afterClosed().subscribe(result => {

      this.confirm = result;

      if (this.confirm) {

        this.selectedBooking.emit(booking);

        this.changePayment.emit('payments');

        console.log('Booking: : ' + booking);

        console.log('Call from booking: ' + result);

      }

      console.log('The dialog was closed: ' + result);
    });
  }
}

@Component({
  selector: '../../components/payments/payment-dialog',
  templateUrl: '../../components/payments/payment-dialog.html',
  styleUrls: ['../../components/payments/payment-dialog.css']
})
export class ListDialog {

  constructor(
    public dialogRef: MatDialogRef<ListDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) { }

  onConfirm(): void {
    this.dialogRef.close(true);
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }
}
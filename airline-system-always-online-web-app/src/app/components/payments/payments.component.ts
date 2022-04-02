import { Component, OnInit, Input, Inject, EventEmitter, Output } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Overlay } from '@angular/cdk/overlay';
import { PaymentsService } from '../../services/payments.service';
import { StripeService, Elements, Element as StripeElement, ElementsOptions } from "ngx-stripe";
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { PaymentIntentDTO } from '../../dto/payment-intent-dto';
import { Booking } from '../../models/booking';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';

export interface DialogData {

  confirmDialog: boolean;

  id: string;

}

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.css']
})
export class PaymentsComponent implements OnInit {

  // receive user booking
  @Input()
  selectedBooking: Booking;

  // export change to flight list event
  @Output()
  changeCheckin: EventEmitter<string> = new EventEmitter<string>(); 

  elements: Elements;

  card: StripeElement;

  // optional parameters
  elementsOptions: ElementsOptions = {
    locale: 'es'
  };

  public viewMicroserviceBooking: boolean = false;

  id: string;

  public loading: boolean = false;

  refresh: boolean = false;

  viewPanel: boolean = false;

  viewError: boolean = false;

  deploymentVersion: string;

  data: any;

  constructor(private paymentService: PaymentsService,
    private stripeService: StripeService,
    private toastrService: ToastrService,
    public dialog: MatDialog, public overlay: Overlay) { }

  ngOnInit(): void {

    this.stripeService.elements(this.elementsOptions)
      .subscribe(elements => {
        this.elements = elements;
        // Only mount the element the first time
        if (!this.card) {
          this.card = this.elements.create('card', {
            style: {
              base: {
                iconColor: '#666EE8',
                color: '#31325F',
                lineHeight: '40px',
                fontWeight: 300,
                fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
                fontSize: '18px',
                '::placeholder': {
                  color: '#CFD7E0'
                }
              }
            }
          });
          this.card.mount('#card-element');
        }
      });
  }

  public stripForm = new FormGroup({
    name: new FormControl('', Validators.required)
  });

  buy() {

    const name = this.stripForm.get('name').value;

    this.stripeService
      .createToken(this.card, { name })
      .subscribe(result => {

        if (result.token) {

          const paymnetIntentDTO: PaymentIntentDTO = {

            token: result.token.id,

            amount: this.selectedBooking.totalPrice * 100,

            currency: 'USD',

            description: 'Origin: ' + this.selectedBooking.originLocationCode
              + ' - ' + 'Destination: ' + this.selectedBooking.destinationLocationCode
              + ' - ' + 'Adults: ' + this.selectedBooking.adults + ' - ' + 'Children: ' + this.selectedBooking.childs
          };

          console.log(result.token);

          try {

            this.paymentService.buy(paymnetIntentDTO).toPromise().then(

              rest => {

                console.log('res: ' + JSON.stringify(rest));

                if (rest['errorCode'] === 0) {

                  this.data = rest['data'];

                  this.loading = false;

                  this.viewError = false;

                  console.log('res: ' + JSON.parse(this.data));

                  this.data = JSON.parse(this.data);

                  console.log('id: ', this.data['id']);

                  this.openDialog(this.data['id']);

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

        } else if (result.error) {
          // Error creating the token
          console.log(result.error.message);
        }
      });
  }

  confirm(id: string): void {

    this.paymentService.confirm(id).toPromise().then(

      result => {

        this.toastrService.success('Payment confirm', 'The payment have been confirm', { positionClass: 'toast-top-center', timeOut: 3000 });

        if(result != undefined) {
          this.changeCheckin.emit('checking');
        }

      },
      error => {
        console.log('Error', error);
      });
  }

  cancel(id: string): void {

    this.paymentService.cancel(id).subscribe(

      result => {

        this.toastrService.success('Payment cancel', 'The payment have been cancel', { positionClass: 'toast-top-center', timeOut: 3000 });

      },
      error => {
        console.log('Error', error);
      });
  }

  // dialog
  confirmDialog: boolean;

  openDialog(id: string): void {

    const dialogRef = this.dialog.open(PaymentDialog, {

      width: '400px',

      scrollStrategy: this.overlay.scrollStrategies.noop(),

      data: { confirmDialog: this.confirmDialog, id: id }

    });

    dialogRef.afterClosed().subscribe(result => {

      this.confirmDialog = result;

      if (this.confirmDialog) {
        this.confirm(id);
      } else {
        this.cancel(id);
      }

      console.log('The dialog was closed: ' + result);
    });
  }
}

@Component({
  selector: '../../components/payments/payments-buy-dialog',
  templateUrl: '../../components/payments/payments-buy-dialog.html',
  styleUrls: ['../../components/payments/payments-buy-dialog.css']
})
export class PaymentDialog {

  constructor(
    public dialogRef: MatDialogRef<PaymentDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) { }

  onConfirm(): void {
    this.dialogRef.close(true);
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }
}
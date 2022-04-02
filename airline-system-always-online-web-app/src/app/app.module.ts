import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ListDetailsComponent } from './components/list-details/list-details.component';
import { ListComponent, BookingDialog } from './components/list/list.component';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { AppMaterialModule } from './material-module';
import { BookingComponent } from './components/booking/booking.component';
import { MatDialogModule } from '@angular/material/dialog';
import { LoginComponent } from './components/login/login.component';
import { PaymentsComponent } from './components/payments/payments.component';
import { ToastrModule } from 'ngx-toastr';
import { NgxStripeModule } from 'ngx-stripe';
import { CheckinComponent } from './components/checkin/checkin.component';

@NgModule({ 
  declarations: [
    AppComponent,
    ListDetailsComponent,
    ListComponent,
    BookingComponent,
    AppComponent,
    LoginComponent,
    PaymentsComponent,
    CheckinComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppMaterialModule,
    BrowserAnimationsModule,
    MatDialogModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    NgxStripeModule.forRoot('pk_test_51GxGMaD0tu2WQFEeKsVgATLlRqShNBHCFoKF8ZXSlrOWRGeFSFomGCrkByPyLH1xjhd4uX90exxc8YdIbkKRmV6Y00VJm1xCTV')
  ],
  entryComponents: [],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListComponent } from './components/list/list.component';
import { ListDetailsComponent } from './components/list-details/list-details.component';


const routes: Routes = [
  { path: 'flights', component: ListComponent },
  { path: 'flight-details', component: ListDetailsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

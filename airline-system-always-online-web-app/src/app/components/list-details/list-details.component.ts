import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-list-details',
  templateUrl: './list-details.component.html',
  styleUrls: ['./list-details.component.css']
})
export class ListDetailsComponent implements OnInit {

  constructor() { }

  @Input()
  selectedPosition : number;
  
  ngOnInit(): void {
  }

}

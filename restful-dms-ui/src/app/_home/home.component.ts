import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  items = [
    {name: "0", address: "zero"},
    {name: "1", address: "one"},
    {name: "2", address: "Two"}
  ];
  dataSource = new MatTableDataSource<Owner>();


  public displayedColumns = ['name', 'dateOfBirth', 'address', 'details', 'update', 'delete'
  ];

  constructor() {
    this.dataSource.data = this.items;

  }

  ngOnInit() {
  }


  public redirectToDetails = (id: string) => {

  }

  public redirectToUpdate = (id: string) => {

  }

  public redirectToDelete = (id: string) => {

  }
}


export interface Owner {
  id: string;
  name: string;
  dateOfBirth: Date;
  address: string;
}

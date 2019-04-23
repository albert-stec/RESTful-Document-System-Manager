import {User} from '../models/user';
import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  users: User[] = [];
  currentUser = localStorage.getItem('currentUser');

  constructor() {}

  ngOnInit() {

    // this.userService
    //   .getAll()
    //   .pipe(first())
    //   .subscribe(users => {
    //     this.users = users;
    //   });
  }
}

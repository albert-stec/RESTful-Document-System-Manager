import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../services/authentication.service";
import {User} from "../models/user";
import {EventService} from "../services/event.service";
import {ModalDirective} from "angular-bootstrap-md";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit, AfterViewInit {
  @ViewChild('addDocumentModal')
  addDocumentModal: ModalDirective;

  currentUser: User;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private eventService: EventService,
  ) {
    this.authenticationService.currentUser.subscribe(x =>
      this.currentUser = x);

    this.eventService.closeDocumentModal.subscribe(() =>
      this.addDocumentModal.hide());
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    this.addDocumentModal.onHidden.subscribe(() =>
      this.eventService.resetDocumentModalForm.next());
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }


}

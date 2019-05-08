import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../services/authentication.service";
import {User} from "../models/user";
import {EventService} from "../services/event.service";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent {
  showAddDocumentComponent: boolean = false;
  currentUser: User;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private eventService: EventService,
  ) {
    this.authenticationService.currentUser.subscribe(x =>
      this.currentUser = x);

    this.eventService.hideAddDocumentComponentEvent.subscribe(() =>
      this.showAddDocumentComponent = false
    );
  }

  openAddDocumentModal() {
    this.showAddDocumentComponent = true;
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }


}

import {Component} from '@angular/core';
import {AuthenticationService} from "./services/authentication.service";
import {Router} from "@angular/router";
import {User} from "./models/user";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  currentUser: User;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private translate: TranslateService
  ) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    translate.setDefaultLang('en');
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }
}

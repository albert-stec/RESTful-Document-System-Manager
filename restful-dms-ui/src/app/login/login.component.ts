import {AuthenticationService} from './../services/authentication.service';
import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {first} from 'rxjs/operators';
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  registerForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  showLoginCard = true;
  title = 'Sign in';

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    private userService: UserService
  ) {
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.authenticationService.logout();

    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }

  get f() {
    return this.loginForm.controls;
  }

  get rf() {
    return this.registerForm.controls;
  }

  onLogin() {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.authenticationService
      .login(this.f.username.value, this.f.password.value)
      .pipe(first())
      .subscribe(
        () => {
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.loading = false;

          if (error.status === 401) {
            alert("Invalid username or password. Try again.")
          }
        }
      )
  }

  onRegister() {
    this.userService.register(this.rf.username.value, this.rf.password.value)
      .pipe(first())
      .subscribe(
        response => console.log(JSON.stringify(response)),
        error1 => {
          if (error1.status === 409) {
            alert("Username taken.")
          }
        }
      )
  }

  onSwitchLoginAndRegisterForm() {
    this.submitted = false;
    this.loginForm.reset();
    this.registerForm.reset();
    this.showLoginCard = !this.showLoginCard;
    this.title = this.showLoginCard === true ? 'Sign in' : 'Sign up';
  }
}

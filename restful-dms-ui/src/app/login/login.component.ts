import {AuthenticationService} from './../services/authentication.service';
import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {first} from 'rxjs/operators';
import {UserService} from "../services/user.service";
import { User } from '../models/user';

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
      username: ['', {validators: [Validators.required, Validators.minLength(5)]}],
      password: ['', {
        validators: [Validators.required,
          Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{8,}')
        ]
      }],
      firstName: ['', {
        validators: [Validators.required,
          Validators.pattern('[a-zA-Z]+')
        ]
      }],
      lastName: ['', {
        validators: [Validators.required,
          Validators.pattern('[a-zA-Z]+')
        ]
      }],
      email: ['', {validators: [Validators.required, Validators.email]}],
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
            alert("Invalid username or password. Enter new username and try again.")
          }
        }
      )
  }

  onRegister() {
    this.submitted = true;

    if (this.registerForm.invalid) {
      return;
    }

    const user = new User();
    user.firstName = this.rf.firstName.value;
    user.lastName = this.rf.lastName.value;
    user.password = this.rf.password.value;
    user.email = this.rf.email.value;
    user.username = this.rf.username.value;

    this.userService.register(user)
      .pipe(first())
      .subscribe(
        response => console.log(JSON.stringify(response)),
        err => {
          if (err.status === 409) {
            alert("Username already taken. Please provide a new username and try again.")
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

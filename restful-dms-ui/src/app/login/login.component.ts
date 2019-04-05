import {AuthenticationService} from './../services/authentication.service';
import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {first} from 'rxjs/operators';
import {UserService} from "../services/user.service";
import {ToastrService} from "ngx-toastr";
import {User} from "../models/user";

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
    private userService: UserService,
    private toastr: ToastrService
  ) {
  }

  get loginFormControls() {
    return this.loginForm.controls;
  }

  get registerFormControls() {
    return this.registerForm.controls;
  }

  ngOnInit() {
    this.setupLoginForm();
    this.setupRegisterForm();

    this.authenticationService.logout();
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }

  setupLoginForm() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  setupRegisterForm() {
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
  }

  onLogin() {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.authenticationService
      .login(this.loginFormControls.username.value, this.loginFormControls.password.value)
      .pipe(first())
      .subscribe(
        () => {
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.loading = false;

          if (error.status === 401) {
            this.toastr.error("Enter valid credentials and try again.",
              "Invalid username or password.")
          }
        }
      )
  }

  onRegister() {
    this.submitted = true;

    if (this.registerForm.invalid) {
      return;
    }

    const user: User = this.registerForm.value;

    this.userService.register(user)
      .pipe(first())
      .subscribe(
        () => {
          this.toastr.success('You can now sign in.', 'Registered!');
          this.onSwitchLoginAndRegisterForm();
        },
        err => {
          if (err.status === 409) {
            this.toastr.error("Try again with different one.",
              err.message)
          }
        })
  }

  onSwitchLoginAndRegisterForm() {
    this.submitted = false;
    this.loginForm.reset();
    this.registerForm.reset();
    this.showLoginCard = !this.showLoginCard;
    this.title = this.showLoginCard === true ? 'Sign in' : 'Sign up';
  }
}

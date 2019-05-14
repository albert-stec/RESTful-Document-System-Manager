import {AuthenticationService} from './../services/authentication.service';
import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from "../services/user.service";
import {ToastrService} from "ngx-toastr";
import {User} from "../models/user";
import {TranslateService} from "@ngx-translate/core";
import {HttpError} from "../models/http-error";

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
  title = 'login.signIn';
  showSpinner: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private toastr: ToastrService,
    private translate: TranslateService
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
      .subscribe(
        () => {
          this.router.navigate([this.returnUrl]);
        },
        error => {

          if (error.status === HttpError.Unauthorized) {
            this.toastr.error(this.translate.instant('login.credentialsErrorMsg'),
              this.translate.instant('login.credentialsErrorTitle'))
          }
        }
      ).add(() =>
      this.loading = false
    )
  }

  onRegister() {
    this.submitted = true;

    if (this.registerForm.invalid) {
      return;
    }

    const user: User = this.registerForm.value;

    this.loading = true;
    this.userService.register(user)
      .subscribe(
        () => {
          this.toastr.success(this.translate.instant('login.registerSuccessMsg'),
            this.translate.instant('login.registerSuccessTitle'));
          this.onSwitchLoginAndRegisterForm();
        },
        err => {
          if (err.status === HttpError.Conflict) {
            this.toastr.error(this.translate.instant('login.registerErrorMsg'),
              err.message)
          }
        }).add(() => this.loading = false)
  }

  onSwitchLoginAndRegisterForm() {
    this.submitted = false;
    this.loginForm.reset();
    this.registerForm.reset();
    this.showLoginCard = !this.showLoginCard;
    this.title = this.showLoginCard === true ? 'login.signIn' : 'login.signUp';
  }
}

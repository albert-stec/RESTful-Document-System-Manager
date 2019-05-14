import {Injectable, Injector} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {AuthenticationService} from '../services/authentication.service';
import {ToastrService} from "ngx-toastr";
import {TranslateService} from "@ngx-translate/core";
import {HttpError} from "../models/http-error";
import {RollbarService} from "../app.module";


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private authenticationService: AuthenticationService,
              private toastr: ToastrService,
              private translate: TranslateService,
              private injector: Injector
  ) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError(err => {
        const error = {
          'status': err.status,
          'message': err.error.message || err.message || err.statusText
        };

      const rollbar = this.injector.get(RollbarService);
      rollbar.error(error);

        console.log(JSON.stringify(err, null, 2));
        console.log(JSON.stringify(event, null, 2));

        if (err.status === HttpError.Unauthorized) {
          this.handleUnauthorizedError(request);
        }

        if (err.status === HttpError.Offline) {
          this.showServerOfflineToast();
        }

        if (HttpError.isInternalServerError(err.status)) {
          this.showUnexpectedErrorToast();
        }

        return throwError(error);
      }
    ))
  }

  handleUnauthorizedError(request) {
    this.authenticationService.logout();

    if (!request.url.includes('login')) {
      location.reload(true);
    }
  }

  showServerOfflineToast() {
    this.toastr.error(this.translate.instant('shared.tryAgainLaterErrorMsg'),
      this.translate.instant('shared.offlineErrorTitle'));
  }

  showUnexpectedErrorToast() {
    this.toastr.error(this.translate.instant('shared.tryAgainLaterErrorMsg'),
      this.translate.instant('shared.unexpectedErrorTitle'));
  }
}

export class HttpError {
  static Offline = 0;
  static BadRequest = 400;
  static Unauthorized = 401;
  static Forbidden = 403;
  static NotFound = 404;
  static TimeOut = 408;
  static Conflict = 409;
  static InternalServerError = 500;

  static isInternalServerError(status): boolean {
    return status.toString()[0] == 5;
  }
}

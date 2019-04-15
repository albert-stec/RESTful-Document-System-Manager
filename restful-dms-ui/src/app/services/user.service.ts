import {User} from '../models/user';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../environments/environment";


@Injectable({ providedIn: 'root' })
export class UserService {
  private apiHost: string = environment.apiHost;

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get<User[]>(this.apiHost + "/users");
  }

  register(user) {
    return this.http.post<any>(this.apiHost + '/users', user, {observe: 'response', });
  }
}

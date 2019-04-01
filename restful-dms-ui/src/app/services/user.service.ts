import { User } from '../models/user';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {config} from 'rxjs';
import {environment} from "../../environments/environment";


@Injectable({ providedIn: 'root' })
export class UserService {
  private apiHost: string = environment.apiHost;

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get<User[]>(this.apiHost + "/users");
  }

  register(username, password) {
    return this.http.post<any>(this.apiHost + '/users', {username, password}, {observe: 'response', });
  }
}

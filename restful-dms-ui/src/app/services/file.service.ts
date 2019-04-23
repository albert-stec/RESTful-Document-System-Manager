import {User} from '../models/user';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../environments/environment";


@Injectable({providedIn: 'root'})
export class DocumentService {
  private apiHost: string = environment.apiHost;

  constructor(private http: HttpClient) {
  }

  getAll() {
    return this.http.get<User[]>(this.apiHost + "/documents");
  }

  register(document) {
    return this.http.post<any>(this.apiHost + '/documents', document, {observe: 'response',});
  }
}

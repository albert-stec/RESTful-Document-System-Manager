import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../environments/environment";
import {Document} from "../models/document";
import {ApiResource} from "../models/endpoint";


@Injectable({providedIn: 'root'})
export class DocumentService {
  private contextResourceUrl: string = environment.apiHost + ApiResource.documents;

  constructor(private http: HttpClient) {
  }

  getAll() {
    return this.http.get<Document[]>(this.contextResourceUrl);
  }

  getById(id) {
    return this.http.get<Document>(this.contextResourceUrl + '/' + id);
  }

  upload(document) {
    return this.http.post<any>(this.contextResourceUrl, document,
      {observe: 'response'});
  }
}

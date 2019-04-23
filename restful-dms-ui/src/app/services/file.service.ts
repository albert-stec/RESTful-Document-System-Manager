import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../environments/environment";
import {Document} from "../models/document";


@Injectable({providedIn: 'root'})
export class DocumentService {
  private apiHost: string = environment.apiHost;

  constructor(private http: HttpClient) {
  }

  getAll() {
    return this.http.get<Document[]>(this.apiHost + "/documents");
  }

  getById(id) {
    // const httpOptions = {
    //   'responseType'  : 'arraybuffer' as 'json'
    //   //'responseType'  : 'blob' as 'json'        //This also worked
    // };
    return this.http.get<Document>(this.apiHost + "/documents/" + id);
  }

  register(document) {
    return this.http.post<any>(this.apiHost + '/documents', document, {observe: 'response'});
  }
}

import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';


@Injectable({providedIn: 'root'})
export class EventService {
  closeDocumentModal = new Subject();
  resetDocumentModalForm = new Subject();

  constructor() {
  }

}

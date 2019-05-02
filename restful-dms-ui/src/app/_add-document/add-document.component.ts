import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {EventService} from "../services/event.service";
import {ModalDirective} from "angular-bootstrap-md";

@Component({
  selector: 'app-add-document',
  templateUrl: './add-document.component.html',
  styleUrls: ['./add-document.component.scss']
})
export class AddDocumentComponent implements AfterViewInit {
  @ViewChild('addDocumentModal')
  addDocumentModal: ModalDirective;

  addDocumentForm: FormGroup;

  constructor(
    private eventService: EventService,
    private formBuilder: FormBuilder,
  ) {
    this.addDocumentForm = this.formBuilder.group({
      title: ['', Validators.required]
    });
  }

  closeModal() {
    this.addDocumentModal.hide();
  }

  ngAfterViewInit(): void {
    this.addDocumentModal.show();
    // this.addDocumentModal.onHidden.subscribe(() =>
    //   this.eventService.hideAddDocumentComponentEvent.next()
    // )
  }

  modalOnHidden() {
    this.eventService.hideAddDocumentComponentEvent.next();
  }
}

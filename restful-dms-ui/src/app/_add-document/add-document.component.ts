import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {EventService} from "../services/event.service";
import {ModalDirective} from "angular-bootstrap-md";
import {Document} from "../models/document";
import {DocumentService} from "../services/file.service";

@Component({
  selector: 'app-add-document',
  templateUrl: './add-document.component.html',
  styleUrls: ['./add-document.component.scss']
})
export class AddDocumentComponent implements AfterViewInit {
  addDocumentForm: FormGroup;
  file: File;

  @ViewChild('addDocumentModal')
  addDocumentModal: ModalDirective;

  constructor(
    private eventService: EventService,
    private formBuilder: FormBuilder,
    private documentService: DocumentService
  ) {
    this.addDocumentForm = this.formBuilder.group({
      title: ['', Validators.required],
      brief: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  closeModal() {
    this.addDocumentModal.hide();
  }

  ngAfterViewInit(): void {
    this.addDocumentModal.show();
  }

  modalOnHidden() {
    this.eventService.hideAddDocumentComponentEvent.next();
  }

  onFileChange($event) {
    this.file = $event.target.files[0];
  }

  onSubmit() {
    let document: Document = this.addDocumentForm.value;
    let formData = new FormData();
    formData.append("file", this.file);
    formData.append('documentDto', new Blob([JSON.stringify(document)], {
      type: "application/json"
    }));

    this.sendDocumentCreationRequest(formData);
  }

  sendDocumentCreationRequest(formData) {
    this.documentService
      .upload(formData)
      .subscribe(
        response => {
          console.log(JSON.stringify(response));
        },
        error => {
          console.log(JSON.stringify(error));
        }
      );
  }
}

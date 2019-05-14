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
  showSpinner: boolean = false;
  showSuccessView: boolean = false;
  invalidFileType: boolean = false;
  wasSubmitted: boolean = false;

  @ViewChild('addDocumentModal')
  addDocumentModal: ModalDirective;

  constructor(
    private eventService: EventService,
    private formBuilder: FormBuilder,
    private documentService: DocumentService
  ) {
    this.addDocumentForm = this.formBuilder.group({
      title: ['',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(50)]
      ],
      brief: ['',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(80)]
      ],
      description: ['', Validators.maxLength(255)]
    });
  }

  ngAfterViewInit(): void {
    this.addDocumentModal.show();
  }

  modalOnHidden() {
    this.eventService.hideAddDocumentComponentEvent.next();
  }

  onFileChange($event) {
    this.file = $event.target.files[0];
    this.invalidFileType = this.file.type != 'application/pdf';
  }

  onSubmit() {
    this.wasSubmitted = true;

    if (this.addDocumentForm.invalid || !this.file || this.invalidFileType) {
      return;
    }

    this.showSpinner = true;

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
        () => {
          this.showSuccessView = true;
        }
      ).add(() => this.showSpinner = false);
  }

  get title() {
    return this.addDocumentForm.get('title');
  }

  get brief() {
    return this.addDocumentForm.get('brief');
  }

  get description() {
    return this.addDocumentForm.get('description');
  }
}


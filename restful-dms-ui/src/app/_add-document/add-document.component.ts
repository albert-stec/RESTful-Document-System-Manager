import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {EventService} from "../services/event.service";

@Component({
  selector: 'app-add-document',
  templateUrl: './add-document.component.html',
  styleUrls: ['./add-document.component.scss']
})
export class AddDocumentComponent implements OnInit {
  addDocumentForm: FormGroup;

  constructor(
    private eventService: EventService,
    private formBuilder: FormBuilder,
  ) {
    this.addDocumentForm = this.formBuilder.group({
      title: ['', Validators.required]
    });

    eventService.resetDocumentModalForm.subscribe(() =>
      this.addDocumentForm.reset());
  }

  ngOnInit() {
  }

  closeModal() {
    this.eventService.closeDocumentModal.next();
  }
}

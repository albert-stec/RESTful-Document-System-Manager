import {Component, OnInit} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";
import {Document} from "../models/document";
import {DocumentService} from "../services/file.service";
import {Mime} from "../models/mime";

@Component({
  selector: 'app-file',
  templateUrl: './file.component.html',
  styleUrls: ['./file.component.scss']
})
export class FileComponent implements OnInit {
  file;
  viewFileUrl;
  base64File;

//
  constructor(private sanitizer: DomSanitizer, private documentService: DocumentService) {
  }

  ngOnInit() {
  }

  changeListener($event): void {
    this.readThis($event.target);
  }

  static populateDocumentData(): Document {
    let document: Document = new Document();
    document.brief = "testBrief";
    document.description = "testDescription";

    return document;
  }

  readThis(inputValue: any): void {
    let file: File = inputValue.files[0];
    let myReader: FileReader = new FileReader();

    myReader.onloadend = () => {
      this.base64File = myReader.result;
      console.log(this.base64File)
      const document = FileComponent.populateDocumentData();

      let formData = new FormData();
      formData.append("file", file);
      formData.append('documentDto', new Blob([JSON.stringify(document)], {
        type: "application/json"
      }));

      this.sendDocumentSaveRequest(formData);
    };

    myReader.readAsDataURL(file);
  }

  sendDocumentSaveRequest(document) {
    this.documentService
      .upload(document)
      .subscribe(
        response => {
          console.log(JSON.stringify(response));
        },
        error => {
          console.log(JSON.stringify(error));
        }
      );
  }

  sanitaze(url) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }

  getDocument() {
    this.documentService
      .getById(4)
      .subscribe(
        response => {
          let brief: string = response.brief;

          this.viewFileUrl = Mime['pdf'] + response.file;
        },
        error1 => {
          console.log(error1)
        }
      )
  }

  // this.pdf = window.URL.createObjectURL(blob).replace('application/pdf', 'application/octet-stream');

  // setupUploadForm() {
  //   this.uploadForm = this.formBuilder.group({
  //     profile: ['']
  //   });
  // }

  // onFileSelect(event) {
  //   if (event.target.files.length > 0) {
  //     const file = event.target.files[0];
  //     this.uploadForm.get('profile').setValue(file);
  //   }
  // }
  //
  // onUpload() {
  //   const formData = new FormData();
  //   formData.append('file', this.uploadForm.get('profile').value);
  //   console.log(formData);
  //   // this.httpClient.post<any>(this.SERVER_URL, formData).subscribe(
  //   //   (res) => console.log(res),
  //   //   (err) => console.log(err)
  //   // );
  // }

  // onFileSelect(event) {
  //   this.file = event.target.files[0];
  //   return new Promise((resolve, reject) => {
  //     const reader = new FileReader();
  //     reader.readAsDataURL(this.file);
  //     reader.onload = () => resolve(reader.result);
  //     reader.onerror = error => reject(error);
  //   });
  // }
  //
  // onUpload(event) {
  //   if (event.target.value) {
  //     // const file = event.target.files[0];
  //     // const type = file.type;
  //     this.onFileSelect(this.file).then((base64: string): any => {
  //       console.log('base64');
  //       console.log(base64);
  //       // this.fileBlob = this.b64Blob([base64], type);
  //       // console.log(this.fileBlob)
  //     });
  //   } else alert('Nothing')
  // }


}

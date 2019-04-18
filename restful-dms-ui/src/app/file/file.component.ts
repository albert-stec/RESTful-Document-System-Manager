import {Component, OnInit} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";

// import {saveAs} from 'file-saver/FileSaver';

@Component({
  selector: 'app-file',
  templateUrl: './file.component.html',
  styleUrls: ['./file.component.scss']
})
export class FileComponent implements OnInit {
  image;
  pdf;
  file;

  constructor(private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
  }

  changeListener($event): void {
    this.readThis($event.target);
  }

  readThis(inputValue: any): void {
    var file: File = inputValue.files[0];
    var myReader: FileReader = new FileReader();

    myReader.onloadend = () => {
      this.pdf = myReader.result;
    }

    const blob = myReader.readAsDataURL(file);

    // url
    // this.pdf = window.URL.createObjectURL(blob).replace('application/pdf', 'application/octet-stream');
  }

  sanitaze() {
    return this.sanitizer.bypassSecurityTrustResourceUrl(this.pdf);
  }



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

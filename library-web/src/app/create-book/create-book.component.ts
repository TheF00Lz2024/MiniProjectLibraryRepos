import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, FormsModule, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { ErrorStateMatcher } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatToolbarModule } from '@angular/material/toolbar';
import { AuthorApiService } from '../service/author-api.service';
import { bookData } from '../model/apiResponse';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-create-book',
  standalone: true,
  imports: [
    MatToolbarModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './create-book.component.html',
  styleUrl: './create-book.component.css'
})
export class CreateBookComponent {
  // flag for show loading
  showLoading: boolean = false;
  // flag for checking if image is choosen for the book
  validBookImage: boolean = false;
  // error state matcher
  matcher = new MyErrorStateMatcher();
  // store book image info
  selectedImage?: FileList;

  // store created book data
  createdBook = {} as bookData;

  // create datasource to show image selected
  dataSource = [] as any;

  // create form group via reactive form
  authorAddBook: FormGroup;

  //resetting of the file value
  @ViewChild('uploadBookImage')
  fileValue!: ElementRef;

  get isbnControl(): FormControl {
    return this.authorAddBook.get('isbn') as FormControl;
  }
  get titleControl(): FormControl {
    return this.authorAddBook.get('title') as FormControl;
  }
  get authorNameControl(): FormControl {
    return this.authorAddBook.get('authorName') as FormControl;
  }

  //create constructor to build form, use custom services
  constructor(private formBuilder: FormBuilder, private authorAPI: AuthorApiService) {
    this.authorAddBook = this.formBuilder.group({
      isbn: this.formBuilder.control('', [Validators.required,
      Validators.pattern('^\\d{3}-\\d-\\d{2}-\\d{6}-\\d$')
      ]),
      title: this.formBuilder.control('', [Validators.required]),
      authorName: this.formBuilder.control('', [Validators.required]),
    })
  }

  //event for selection of book image
  selectBookImage(event: any){
    console.log(this.fileValue);
    // per upload create make datasource empty
    this.dataSource = [];
    this.selectedImage = event.target.files;
    console.log(this.selectedImage);
    if(this.selectedImage != undefined && this.selectedImage.length!=0){
      // check for valid file type (accept only jpeg / png)
      if(this.selectedImage[0].type == "image/png" || this.selectedImage[0].type == "image/jpeg"){
        this.validBookImage = true;
        const reader = new FileReader();
        reader.onload = (e: any) => {
          this.createdBook = {
            'isbn': `${this.isbnControl.value}`,
            'title': `${this.titleControl.value}`,
            'authorName': `${this.authorNameControl.value}`,
            'imgData': `${e.target.result}`
          };
          let bookImage = {
            img: `${e.target.result}`,
            imgName: `${this.selectedImage![0].name}`,
            imgSize: `${((this.selectedImage![0].size / (1024*1024)) * 100 | 0) / 100}MB`
          }
          this.dataSource.push(bookImage);
        };
        reader.readAsDataURL(this.selectedImage[0]);
      }
      // if once is ture user reslect another file type change it to false
      else{
        this.validBookImage = false;
      }

    }
  }

  uploadBook() {
    this.showLoading = true;
    this.authorAPI.uploadBook(this.createdBook)
    .subscribe({
      next:(data) => {
        setTimeout(() => {
          alert(`Book uploaded successfully!\n`+
            `ISBN: ${data.isbn}\n`+
            `Title: ${data.title}\n`+
            `Author Name: ${data.authorName}`);
          // reset all the form value after submittion
          this.isbnControl.reset();
          this.titleControl.reset();
          this.authorNameControl.reset();
          this.fileValue.nativeElement.value = '';
          // reset the image selected to undefined (to mimic form reset)
          this.selectedImage = undefined;
          // set the check back to false
          this.validBookImage = false;
          this.showLoading = false;
        }, 3000);
      }, error:(error) => {
        alert(error.error.message);
        this.showLoading = false;
      }
    });
  }
}

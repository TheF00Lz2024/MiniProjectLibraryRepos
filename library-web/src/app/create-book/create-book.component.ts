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
  bookImageSelected: boolean = false;
  // error state matcher
  matcher = new MyErrorStateMatcher();
  // store book image info
  selectedImage?: FileList;
  selectedImageNames: string[] = [];
  imagePreview: string[] = [];

  // store created book data
  createdBook = {} as bookData;

  // create form group via reactive form
  authorAddBook: FormGroup;

  // test reset for file
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
    this.selectedImageNames = [];
    this.selectedImage = event.target.files;
    if(this.selectedImage != undefined){
      this.bookImageSelected = true;
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.createdBook = {
          'isbn': `${this.isbnControl.value}`,
          'title': `${this.titleControl.value}`,
          'authorName': `${this.authorNameControl.value}`,
          'imgData': `${e.target.result}`
        };
        this.imagePreview.push(e.target.result);
      };
      reader.readAsDataURL(this.selectedImage[0]);
      this.selectedImageNames.push(this.selectedImage[0].name);
    }
  }

  uploadBook() {
    this.showLoading = true;
    this.authorAPI.uploadBook(this.createdBook)
    .subscribe({
      next:(data) => {
        setTimeout(() => {
          alert(`Book uploaded successfully!\n`+
            `ISBN: ${this.isbnControl.value}\n`+
            `Title: ${this.titleControl.value}\n`+
            `Author Name: ${this.authorNameControl.value}`);
          this.isbnControl.reset();
          this.titleControl.reset();
          this.authorNameControl.reset();
          this.fileValue.nativeElement.value = '';
          this.showLoading = false;
        }, 3000);
      }, error:(error) => {
        alert(error.error.message);
        this.showLoading = false;
      }
    });
  }
}

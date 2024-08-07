import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, FormsModule, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { ErrorStateMatcher } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatToolbarModule } from '@angular/material/toolbar';

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
  // error state matcher
  matcher = new MyErrorStateMatcher();


  // create form group via reactive form
  userSignUpForm: FormGroup;
  get usernameControl(): FormControl {
    return this.userSignUpForm.get('username') as FormControl;
  }
  get passwordControl(): FormControl {
    return this.userSignUpForm.get('password') as FormControl;
  }
  get confirmPasswordControl(): FormControl {
    return this.userSignUpForm.get('confirmPassword') as FormControl;
  }

  //create constructor to build form, use custom services
  constructor(private formBuilder: FormBuilder) {
    this.userSignUpForm = this.formBuilder.group({
      username: this.formBuilder.control('', [Validators.required]),
      password: this.formBuilder.control('', [Validators.required]),
      confirmPassword: this.formBuilder.control('', [Validators.required])
    })
  }
}

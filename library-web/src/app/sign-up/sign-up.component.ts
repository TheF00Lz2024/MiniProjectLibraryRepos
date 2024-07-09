import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, FormsModule, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [
    MatIconModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule],
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css'
})
export class SignUpComponent {
  @Output() backToLogin = new EventEmitter<boolean>();

  // hide password and confirm password
  hidePassword: boolean = false;
  hideConfirmPassword: boolean = false;
  
  matcher = new MyErrorStateMatcher();

  // create form group via reactive form
  userSignUpForm: FormGroup;
  get usernameControl(): FormControl{
    return this.userSignUpForm.get('username') as FormControl;
  }
  get passwordControl(): FormControl{
    return this.userSignUpForm.get('password') as FormControl;
  }
  get confirmPasswordControl(): FormControl{
    return this.userSignUpForm.get('confirmPassword') as FormControl;
  }

  //create constructor to build form, use custom services
  constructor(private formBuilder: FormBuilder){
    this.userSignUpForm = this.formBuilder.group({
      username: this.formBuilder.control('', [Validators.required, Validators.email]),
      password: this.formBuilder.control('', [Validators.required]),
      confirmPassword: this.formBuilder.control('',Validators.required)
    })
  }

  // back to login
  signUp() {
    this.backToLogin.emit(true);
  }
}

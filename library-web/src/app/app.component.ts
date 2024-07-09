import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { SignUpComponent } from './sign-up/sign-up.component';
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MatToolbarModule, 
    MatFormFieldModule, 
    MatInputModule, 
    MatButtonModule, 
    MatIconModule,
    CommonModule, 
    SignUpComponent, 
    ReactiveFormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'library-web';
  userLogin: boolean = true;
  // hide password and confirm password
  hidePassword: boolean = false;
  matcher = new MyErrorStateMatcher();

  //create form group for log in
  userLoginForm: FormGroup;
  get usernameControl(): FormControl {
    return this.userLoginForm.get('username') as FormControl;
  }
  get passwordControl(): FormControl {
    return this.userLoginForm.get('password') as FormControl;
  }

  constructor(private formBuilder: FormBuilder) {
    this.userLoginForm = this.formBuilder.group({
      username: this.formBuilder.control('', [Validators.required, Validators.email]),
      password: this.formBuilder.control('', [Validators.required])
    })
  }

  //function to listen child event (sign-up) to hide sign up page
  login($event: boolean) {
    this.usernameControl.reset();
    this.passwordControl.reset();
    this.userLogin = $event;
  }

  //function for creating account
  createdAccount() {
    this.userLogin = false;
  }
}

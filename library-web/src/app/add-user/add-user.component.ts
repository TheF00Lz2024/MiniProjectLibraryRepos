import { Component} from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, FormsModule, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { LoginApiService } from '../service/login-api.service';
import { user } from '../model/apiResponse';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-add-user',
  standalone: true,
  imports: [
    MatIconModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './add-user.component.html',
  styleUrl: './add-user.component.css'
})
export class AddUserComponent {

  // hide password and confirm password
  hidePassword: boolean = false;
  hideConfirmPassword: boolean = false;

  matcher = new MyErrorStateMatcher();

  // hiding overlay containing spinning tool
  showLoading: boolean = false;

  // create form group via reactive form
  createAuthorForm: FormGroup;
  get usernameControl(): FormControl {
    return this.createAuthorForm.get('username') as FormControl;
  }
  get passwordControl(): FormControl {
    return this.createAuthorForm.get('password') as FormControl;
  }
  get confirmPasswordControl(): FormControl {
    return this.createAuthorForm.get('confirmPassword') as FormControl;
  }

  //create constructor to build form, use custom services
  constructor(private formBuilder: FormBuilder, private loginAPIService: LoginApiService) {
    this.createAuthorForm = this.formBuilder.group({
      username: this.formBuilder.control('', [Validators.required, Validators.email]),
      password: this.formBuilder.control('', [Validators.required, Validators.pattern(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/)]),
      confirmPassword: this.formBuilder.control('', [Validators.required, Validators.pattern(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/)])
    })
  }

  //create account
  createAccount() {
    const newUser = {
      username: `${this.usernameControl.value}`,
      password: `${this.passwordControl.value}`,
      roles: "Author"
    } as user;
    this.showLoading = true;

    this.loginAPIService.createUserAccount(newUser)
      .subscribe({
        next: (data) => {
          // show the effect of loading
          setTimeout(() => {
            this.usernameControl.reset();
            this.passwordControl.reset();
            this.confirmPasswordControl.reset();
            alert(`Username: ${data.username} has been created!`);
            console.log(`Username: ${data.username} has been created!`);
            this.showLoading = false;
          }, 3000);
        }, error: ((error) => {
          if(error.status == 0){
            alert("Please send support ticket to helpdesk!");
            console.log("Unable to connect to server!");
          }else{
            console.log(error.error.message);
            alert(error.error.message);
          }
          this.showLoading = false;
        })
      })
  }

}

import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { SignUpComponent } from './sign-up/sign-up.component';
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { LoginApiService } from './service/login-api.service';
import { mergeMap } from 'rxjs';
import { SessionStorageService } from './service/session-storage.service';
import { LibraryApiService } from './service/library-api.service';
import { MatProgressSpinner } from '@angular/material/progress-spinner';

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
    ReactiveFormsModule,
    MatProgressSpinner],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'library-web';
  userLogin: boolean = true;
  // hide password and confirm password
  hidePassword: boolean = false;
  matcher = new MyErrorStateMatcher();

  // show loading
  showLoading: boolean = false;

  //create form group for log in
  userLoginForm: FormGroup;
  get usernameControl(): FormControl {
    return this.userLoginForm.get('username') as FormControl;
  }
  get passwordControl(): FormControl {
    return this.userLoginForm.get('password') as FormControl;
  }

  constructor(private formBuilder: FormBuilder, private loginAPIService: LoginApiService, private libraryApiService: LibraryApiService, private sessionStorage: SessionStorageService) {
    this.userLoginForm = this.formBuilder.group({
      username: this.formBuilder.control('', [Validators.required, Validators.email]),
      password: this.formBuilder.control('', [Validators.required])
    })
  }

  //function for login to account
  loginToAccount() {
    // show the loading 
    this.showLoading = true;
    this.loginAPIService.getUserLogin(this.usernameControl.value, this.passwordControl.value)
      .pipe(mergeMap((data) => {
        this.sessionStorage.setTokenSession(data.token);
        return this.libraryApiService.getUserRoles(data.token);
      })).subscribe({
        next: (data) => {
          // show the effect of loading
          setTimeout(() => {
            this.sessionStorage.setUsernameSession(this.usernameControl.value);
            this.sessionStorage.setUserRoleSession(data.role);
            console.log("You have successfully login");
            this.showLoading = false;
          }, 3000);
        }, error: (error) => {
          console.log(error.error.message);
          alert(error.error.message);
          this.showLoading = false;
        }
      })
  }

  //function to listen child event (sign-up) to hide sign up page
  backToLogin($event: boolean) {
    this.usernameControl.reset();
    this.passwordControl.reset();
    this.userLogin = $event;
  }

  //function for creating account
  createAccount() {
    this.userLogin = false;
  }
}

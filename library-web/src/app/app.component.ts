import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { SignUpComponent } from './sign-up/sign-up.component';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MatToolbarModule, MatFormFieldModule, MatInputModule, MatButtonModule, CommonModule, SignUpComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'library-web';
  userLogin: boolean = true;

  //function for creating account
  createdAccount() {
    this.userLogin = false;
  }
}

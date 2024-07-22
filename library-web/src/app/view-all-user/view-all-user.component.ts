import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AdminApiService } from '../service/admin-api.service';

@Component({
  selector: 'app-view-all-user',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './view-all-user.component.html',
  styleUrl: './view-all-user.component.css'
})
export class ViewAllUserComponent implements OnInit {

  ngOnInit(): void {
    this.adminAPI.getAllUser()
      .subscribe({
        next: (data) => {
          console.log(data);
        }, error: (error) => {
          console.log(error.error.message);
        }
      })
  }

  //create constructor to build form, use custom services
  constructor(private adminAPI: AdminApiService) { }
}

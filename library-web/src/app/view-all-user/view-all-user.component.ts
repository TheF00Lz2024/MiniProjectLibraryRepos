import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { AdminApiService } from '../service/admin-api.service';
import { formattedUser, user } from '../model/apiResponse';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-view-all-user',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatIconModule,
    CommonModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatButtonModule
  ],
  templateUrl: './view-all-user.component.html',
  styleUrl: './view-all-user.component.css'
})
export class ViewAllUserComponent implements OnInit {

  //show loading effect
  showLoading: boolean = false;
  //show prompt in overlay for confirmation of deleting of account
  deleteAccount: boolean = false;
  //store username and dispplay in overlay
  deleteAccountUsername: string = '';
  //store filter value
  storeFilterValue: string = '';

  // set up the column name for the table
  displayedColumns: string[] = ['username', 'roles', 'edit'];

  dataSource!: MatTableDataSource<formattedUser>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  //bind the table render to ng OnInit event
  ngOnInit(): void {
    this.showLoading = true;
    this.getDataSource(false);
  }

  // filter event to filter by username
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeFilterValue = filterValue.trim();
    this.dataSource.filter = this.storeFilterValue;
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  //create constructor to build form, use custom services
  constructor(private adminAPI: AdminApiService) { }

  //create a function to call the database and update the datasource
  getDataSource(isFromDelete: boolean) {
    // call API service to get all user data
    this.adminAPI.getAllUser()
      .subscribe({
        next: (data) => {
          setTimeout(() => {
            this.dataSource = new MatTableDataSource(data);
            console.log(this.dataSource);
            this.dataSource.paginator = this.paginator;
            if (isFromDelete) {
              this.dataSource.filter = this.storeFilterValue;
              alert(`${this.deleteAccountUsername} account has been deleted!`);
            }
            this.showLoading = false;
          }, 3000)
        }, error: (error) => {
          console.log(error.error.message);
          this.showLoading = false;
        }
      })
  }

  //button event to delete user account
  deleteUserAccount(username: string) {
    this.showLoading = true;
    // store the selected username
    this.deleteAccountUsername = username;
    this.deleteAccount = true;
  }

  //button event to confirm the deletion of account
  confirmDeleteUserAccount() {
    this.deleteAccount = false;
    this.adminAPI.deleteUser(this.deleteAccountUsername)
      .subscribe({
        next: (data) => {
          setTimeout(() => {
            // call function to update datasource
            this.getDataSource(true);
          }, 3000);
        }, error: (error) => {
          console.log(error.error.message);
          alert(error.error.message);
          this.showLoading = false;
        }
      })
  }
}

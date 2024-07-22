import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { AdminApiService } from '../service/admin-api.service';
import { user } from '../model/apiResponse';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-view-all-user',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatIconModule
  ],
  templateUrl: './view-all-user.component.html',
  styleUrl: './view-all-user.component.css'
})
export class ViewAllUserComponent implements OnInit {

  // set up the column name for the table
  displayedColumns: string[] = ['username', 'roles', 'edit'];

  dataSource!: MatTableDataSource<user>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  //bind the table render to ng OnInit event
  ngOnInit(): void {
    // call API service to get all user data
    this.adminAPI.getAllUser()
      .subscribe({
        next: (data) => {
          this.dataSource = new MatTableDataSource(data);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        }, error: (error) => {
          console.log(error.error.message);
        }
      })
  }

  // filter event to filter by username
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  //create constructor to build form, use custom services
  constructor(private adminAPI: AdminApiService) { }

  //button event to delete user account
  deleteUserAccount(username: string) {
    console.log(username);
  }

}

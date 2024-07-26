import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule, MatPaginator } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSortModule, MatSort } from '@angular/material/sort';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { bookData, formattedUser } from '../model/apiResponse';
import { LibraryApiService } from '../service/library-api.service';

@Component({
  selector: 'app-view-all-book',
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
  templateUrl: './view-all-book.component.html',
  styleUrl: './view-all-book.component.css'
})
export class ViewAllBookComponent {

  //show loading effect
  showLoading: boolean = false;

  // set up the column name for the table
  displayedColumns: string[] = ['isbn', 'title', 'authorName'];

  dataSource!: MatTableDataSource<bookData>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  //bind the table render to ng OnInit event
  ngOnInit(): void {
    this.showLoading = true;
    this.getDataSource();
  }

  // filter event to filter by username
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue;
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  //create constructor to build form, use custom services
  constructor(private libraryService: LibraryApiService) { }

  //create a function to call the database and update the datasource
  getDataSource() {
    // call API service to get all user data
    this.libraryService.getAllBook()
      .subscribe({
        next: (data) => {
          setTimeout(() => {
            this.dataSource = new MatTableDataSource(data);
            this.dataSource.paginator = this.paginator;
            this.showLoading = false;
          }, 3000)
        }, error: (error) => {
          console.log(error.error.message);
          this.showLoading = false;
        }
      })
  }
}

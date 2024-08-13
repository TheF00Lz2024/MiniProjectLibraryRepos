import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule, MatPaginator } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSortModule, MatSort } from '@angular/material/sort';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { bookData } from '../model/apiResponse';
import { LibraryApiService } from '../service/library-api.service';
import {MatTabChangeEvent, MatTabsModule} from '@angular/material/tabs';

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
    MatButtonModule,
    MatTabsModule
  ],
  templateUrl: './view-all-book.component.html',
  styleUrl: './view-all-book.component.css'
})
export class ViewAllBookComponent {

  // show loading effect
  showLoading: boolean = false;

  // set up the column name for the table
  displayedColumns: string[] = ['isbn', 'title', 'authorName'];

  // store placeholder value for filter
  plcaeHolder: string = '978-3-16-148410-0';

  // store filter selection
  filterSelection: string = 'isbn';

  dataSource!: MatTableDataSource<bookData>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  @ViewChild('input')
  filterValue!: ElementRef;

  //bind the table render to ng OnInit event
  ngOnInit(): void {
    this.showLoading = true;
    this.getDataSource();
  }

  // filter event to filter by username
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    if(this.filterSelection == 'isbn'){
      this.dataSource.filterPredicate = function (data, filter: string): boolean {
        return data.isbn.includes(filter);
      };
    }else if(this.filterSelection == 'title'){
      this.dataSource.filterPredicate = function (data, filter: string): boolean {
        return data.title.includes(filter);
      };
    }else{
      this.dataSource.filterPredicate = function (data, filter: string): boolean {
        return data.authorName.includes(filter);
      };
    }
    this.dataSource.filter = filterValue.trim();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  // listen to filter tab event
  tabEvent(event: MatTabChangeEvent){
    if(event.index == 0){
      this.filterSelection = 'isbn';
      this.plcaeHolder = '978-3-16-148410-0';
    } else if(event.index == 1){
      this.filterSelection = 'title';
      this.plcaeHolder = 'Little Red Riding Hood';
    } else{
      this.filterSelection = 'author name';
      this.plcaeHolder = 'John Doe';
    }
    // reset filter value
    this.filterValue.nativeElement.value = '';
    // reset filter
    this.dataSource.filter = "";

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

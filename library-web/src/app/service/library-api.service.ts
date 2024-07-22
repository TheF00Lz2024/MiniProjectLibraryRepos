import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { bookData, userRole } from '../model/apiResponse';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LibraryApiService {

  constructor(private httpClient: HttpClient) { }

  loginAPIUrl = "http://localhost:8083/api/v1/book";

  getHttpHeader(): HttpHeaders{
    return new HttpHeaders().set('Authorization', `Bearer ${sessionStorage.getItem('token')}`);
  }

  getUserRoles(): Observable<userRole>{
    return this.httpClient.get<userRole>(`${this.loginAPIUrl}/user/role`, {'headers': this.getHttpHeader()});
  }
  
  getAllBook(): Observable<bookData[]>{
    return this.httpClient.get<bookData[]>(`${this.loginAPIUrl}`,{'headers': this.getHttpHeader()});
  }
}

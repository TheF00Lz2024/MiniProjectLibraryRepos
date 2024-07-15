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

  getUserRoles(token: string): Observable<userRole>{
    const header = 
      new HttpHeaders()
      .set('Authorization',`Bearer ${token}`);
    return this.httpClient.get<userRole>(`${this.loginAPIUrl}/user/role`,{'headers': header});
  }
  
  getAllBook(token: string): Observable<bookData[]>{
    const header = 
      new HttpHeaders()
        .set('Authorization',`Bearer ${token}`);
    return this.httpClient.get<bookData[]>(`${this.loginAPIUrl}`,{'headers': header});
  }
}

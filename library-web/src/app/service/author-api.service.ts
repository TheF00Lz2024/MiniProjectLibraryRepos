import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { bookData } from '../model/apiResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthorApiService {

  constructor(private httpClient: HttpClient) { }

  private authorAPIUrl = 'http://localhost:8083/api/v1/author';

  getHttpHeader(): HttpHeaders{
    return new HttpHeaders().set('Authorization', `Bearer ${sessionStorage.getItem('token')}`);
  }
  
  uploadBook(data: bookData): Observable<bookData> {
    const body = JSON.stringify(data);
    return this.httpClient.post<bookData>(`${this.authorAPIUrl}`, body, { 'headers': this.getHttpHeader().append( 'content-type', 'application/json') });
  }
}

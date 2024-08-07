import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthorApiService {

  constructor(private httpClient: HttpClient) { }

  private authorAPIUrl = 'http://localhost:8083/api/v1/author';

  getHttpHeader(): HttpHeaders{
    return new HttpHeaders().set('Authorization', `Bearer ${sessionStorage.getItem('token')}`);
  }
  
  uploadBook(data: any): Observable<any> {
    return this.httpClient.post(`${this.authorAPIUrl}`,data, { 'headers': this.getHttpHeader() });
  }
}

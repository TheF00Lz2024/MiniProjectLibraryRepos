import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { loginAPI, user } from '../model/apiResponse';
@Injectable({
  providedIn: 'root'
})
export class AdminApiService {

  constructor(private httpClient: HttpClient) { }

  adminAPIUrl = "http://localhost:8081/api/v1/admin";

  getHttpHeader(): HttpHeaders{
    return new HttpHeaders().set('Authorization', `Bearer ${sessionStorage.getItem('token')}`);
  }

  getAllUser(): Observable<user[]>{
    return this.httpClient.get<user[]>(`${this.adminAPIUrl}`, { 'headers': this.getHttpHeader()});
  }

  deleteUser(username: string): Observable<user>{
    return this.httpClient.delete<user>(`${this.adminAPIUrl}?username=${username}`, { 'headers': this.getHttpHeader()});
  }
}

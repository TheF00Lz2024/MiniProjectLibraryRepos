import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { loginAPI, user } from '../model/apiResponse';


@Injectable({
  providedIn: 'root'
})
export class LoginApiService {

  constructor(private httpClient: HttpClient) { }

  loginAPIUrl = "http://localhost:8081/api/v1/user";
  
  getUserLogin(username: string, password:string): Observable<loginAPI>{
    return this.httpClient.get<loginAPI>(`${this.loginAPIUrl}?username=${username}&password=${password}`);
  }

  createUserAccount(createUser: user): Observable<user>{
    const headers = { 'content-type': 'application/json' }
    const body = JSON.stringify(createUser);
    return this.httpClient.post<user>(`${this.loginAPIUrl}`, body, { 'headers': headers });
  }
}

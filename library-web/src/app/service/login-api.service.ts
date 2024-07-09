import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { loginAPI } from '../model/apiResponse';


@Injectable({
  providedIn: 'root'
})
export class LoginApiService {

  constructor(private httpClient: HttpClient) { }

  loginAPIUrl = "http://localhost:8081/api/v1/user";
  
  getUserLogin(username: string, password:string): Observable<loginAPI>{
    return this.httpClient.get<loginAPI>(`${this.loginAPIUrl}?username=${username}&password=${password}`);
  }
}

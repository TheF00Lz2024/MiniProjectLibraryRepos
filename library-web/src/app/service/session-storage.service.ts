import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionStorageService {

  constructor() { }

  setUsernameSession(username: string){
    sessionStorage.setItem('username', username);
  }

  setSessionToken(token: string){
    sessionStorage.setItem('token', token);
  }

  getUsernameSession(){
    return sessionStorage.getItem('username');
  }

  getSessionToken(){
    return sessionStorage.getItem('token')
  }
}

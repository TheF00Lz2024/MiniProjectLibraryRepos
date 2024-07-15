import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionStorageService {

  constructor() { }

  setUsernameSession(username: string){
    sessionStorage.setItem('username', username);
  }

  setTokenSession(token: string){
    sessionStorage.setItem('token', token);
  }

  setUserRoleSession(role: string){
    sessionStorage.setItem('role', role);
  }

  getUsernameSession(){
    return sessionStorage.getItem('username');
  }

  getTokenSession(){
    return sessionStorage.getItem('token');
  }

  getUserRoleSession(){
    return sessionStorage.getItem('role');
  }
}

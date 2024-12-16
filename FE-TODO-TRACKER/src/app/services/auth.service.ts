
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  constructor(private router:Router) { }
  
  setToken(token: string): void {
    localStorage.setItem("token", token);
    localStorage.setItem("status", "true");
  }

  clearToken(): void {
    localStorage.removeItem("token");
    localStorage.removeItem("status");
  }

  getToken(): string | null {
    return localStorage.getItem("token");
  }

  getStatus(): boolean {
    return localStorage.getItem("status") === "true";
  }
  logoutUser(){
    localStorage.removeItem('token');
    this.router.navigate(['/main']);
  }
  loggedIn(){
    return !!localStorage.getItem('token')
  }
  logout(){
    localStorage.removeItem('token');
    this.router.navigate(['/main']);
  }
}

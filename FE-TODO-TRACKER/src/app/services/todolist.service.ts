import { Task } from '../list-add/list-add.component';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TodolistService {
  api_url = "http://localhost:9000/api/user/userdata/";

  constructor(private http: HttpClient) { }

  getAllUsers(): Observable<any> {
    return this.http.get(`${this.api_url}getAllUser`);
  }

  getAllTodoList(): Observable<Task[]> {
    const token = this.getToken();
    const headers = this.getRequestHeaders(token);
    return this.http.get<Task[]>(`${this.api_url}getAllTodoList`, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  saveTodolist(todoList: Task): Observable<any> {
    const token = this.getToken();
    console.log('Token:', token); // Log the token
    const headers = this.getRequestHeaders(token);
    return this.http.post<any>(`${this.api_url}todolist`, todoList, { headers }).pipe(
      catchError(this.handleError)
    );
  }
  deleteTodoList(todoListId: string): Observable<any> {
    const token = this.getToken();
    const headers = this.getRequestHeaders(token);
    return this.http.delete<any>(`${this.api_url}${todoListId}`, { headers }).pipe(
      catchError(this.handleError)
    );
  }
  updateTodoList(todoListId: string, taskData: Partial<Task>): Observable<any> {
    const token = this.getToken();
    const headers = this.getRequestHeaders(token);
    return this.http.put<any>(`${this.api_url}${todoListId}`, taskData, { headers }).pipe(
      catchError(this.handleError)
    );
  }
  
  

  getUserByEmail(email: string): Observable<any> {
    return this.http.get<any>(`${this.api_url}${email}`);
  }

  private getRequestHeaders(token: string | null): HttpHeaders {
    if (token) {
      return new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      });
    } else {
      return new HttpHeaders({
        'Content-Type': 'application/json'
      });
    }
  }

  private getToken(): string | null {
    return localStorage.getItem('token');
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An error occurred';
    if (error.error instanceof ErrorEvent) {
      // Client-side or network error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Backend error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message || 'Unknown error'}`;
    }
    return throwError(errorMessage);
  }
}
// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable } from 'rxjs';
// import { SignUp } from '../model/SignUp';

// @Injectable({
//   providedIn: 'root'
// })
// export class LoginService {

//   constructor(private http: HttpClient) { }

//   signup(form: SignUp): Observable<any> {
//     const url = "http://localhost:9000/api/user/register";
//     return this.http.post(url, form);
//   }

//   login(credentials: any): Observable<any> {
//     const url = "http://localhost:9000/api/auth/login";
//     return this.http.post(url, credentials);
//   }
// }
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { SignUp } from '../model/SignUp';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private registerUrl = "http://localhost:9000/api/user/register";
  private loginUrl = "http://localhost:9000/api/auth/login";

  constructor(private http: HttpClient, private authService: AuthService) { }

  signup(form: SignUp): Observable<any> {
    return this.http.post(this.registerUrl, form);
  }

  login(credentials: any): Observable<any> {
    return this.http.post<any>(this.loginUrl, credentials).pipe(
      tap(response => {
        if (response.token) {
          this.authService.setToken(response.token);
        }
      })
    );
  }

}

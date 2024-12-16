import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';
import { TodolistService } from '../services/todolist.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginForm: FormGroup;
  passwordVisible: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private loginService: LoginService,
    private todolistService: TodolistService
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, this.passwordValidator]]
    });
  }

  passwordValidator(control: FormControl): { [key: string]: boolean } | null {
    const value = control.value;
    if (!value) {
      return null;
    }
  
    const errors: any = {};
  
    if (value.length > 8) {
      errors.exceedsMaxLength = true;
    }
  
    const hasUpperCase = /[A-Z]/.test(value);
    const hasLowerCase = /[a-z]/.test(value);
    const hasNumeric = /[0-9]/.test(value);
    const hasSpecial = /[!@#$%^&*(),.?":{}|<>]/.test(value);
  
    if (!hasUpperCase) {
      errors.noUpperCase = true;
    }
    if (!hasLowerCase) {
      errors.noLowerCase = true;
    }
    if (!hasNumeric) {
      errors.noNumeric = true;
    }
    if (!hasSpecial) {
      errors.noSpecial = true;
    }
  
    if (Object.keys(errors).length > 0) {
      errors.passwordInvalid = true;  // Add general error if any specific error exists
    }
  
    return Object.keys(errors).length > 0 ? errors : null;
  }
  
  onSubmit() {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;

      this.loginService.login({ email, password }).subscribe(
        response => {
          console.log('Login successful:', response);
          localStorage.setItem('userEmailId', email);
          this.router.navigate(['/list-add']);
        },
        error => {
          console.error('Login error:', error);
          // Display error message to the user
        }
      );
    }
  }
}

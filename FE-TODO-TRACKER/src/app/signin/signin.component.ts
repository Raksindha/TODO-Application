import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {
  signupForm!: FormGroup; // Definite assignment assertion modifier

  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.signupForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, this.passwordValidator]],
      dateOfBirth: [''] // Optional, specify default value or leave it blank
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
    if (this.signupForm.valid) {
      this.loginService.signup(this.signupForm.value)
        .subscribe(
          (response) => {
            console.log('Registration successful!', response);
            this.router.navigate(['/login']);
          },
          (error) => {
            console.error('Error occurred:', error);
            // Handle error response here, if needed
          }
        );
    }
  }
}

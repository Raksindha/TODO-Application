import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  isLoggedIn: boolean = false;

  constructor(public authService: AuthService ,  private router: Router,) {
    // Check if the user is logged in when the component initializes
    // this.isLoggedIn = this.authService.getStatus();
  }


    

  }




import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';
import {  MatSnackBarModule } from '@angular/material/snack-bar';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import { MainComponent } from './main/main.component';
import { HomeComponent } from './home/home.component';
import { SigninComponent } from './signin/signin.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


import { ListAddComponent } from './list-add/list-add.component';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatRadioModule} from '@angular/material/radio';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import {MatMenuModule} from '@angular/material/menu';

import {BrowserAnimationsModule}from '@angular/platform-browser/animations';
import { AboutComponent } from './about/about.component'
import { HttpClientModule } from '@angular/common/http';
import { SideBarComponent } from './side-bar/side-bar.component';


@NgModule({
    declarations: [
        AppComponent,
        HeaderComponent,
        LoginComponent,
        MainComponent,
        HomeComponent,
        SigninComponent,
        ListAddComponent,
        AboutComponent,
        SideBarComponent
   
        
        
    ],
   
    imports: [
        BrowserModule,
        AppRoutingModule,
        MatToolbarModule,
        MatIconModule,
        FormsModule,
        ReactiveFormsModule,
        MatSelectModule,
        MatInputModule,
        MatFormFieldModule,
        MatDatepickerModule,
        MatRadioModule,
        MatButtonModule,
        MatSidenavModule,
        MatListModule,
        MatMenuModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatSnackBarModule
    ],
     providers: [
        provideAnimationsAsync()
    ],
    bootstrap: [AppComponent],
})
export class AppModule { }

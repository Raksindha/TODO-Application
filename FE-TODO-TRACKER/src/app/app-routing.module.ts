import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { MainComponent } from './main/main.component';
import { HeaderComponent } from './header/header.component';
import { SigninComponent } from './signin/signin.component';
import { ListAddComponent } from './list-add/list-add.component';
import { AboutComponent } from './about/about.component';
// import { ListComponent } from './list/list.component';
// import { ListAddComponent } from './list-add/list-add.component';


const routes: Routes = [
  {
    path:'',pathMatch:'full',redirectTo:'/HomeComponent'
  },
  {
    path:'HomeComponent',component:HomeComponent
  },
  {
    path:'header',component:HeaderComponent
  },
  {
    path:'main',component:MainComponent
  },
  {
    path:'login',component:LoginComponent
  },
  {
    path:'signin',component:SigninComponent
  },
  {
    path:'list-add',component:ListAddComponent
  },
  {
    path:'about',component:AboutComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }


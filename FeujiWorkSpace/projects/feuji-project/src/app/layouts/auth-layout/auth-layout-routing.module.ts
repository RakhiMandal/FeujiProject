import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginLayoutComponent } from '../../pages/loginPage/login-layout.component';
import { ForgotPasswordComponent } from '../../pages/forgot-password/forgot-password.component';

const routes: Routes = [
  { path:"login",component:LoginLayoutComponent},
  { path: 'forgot-password', component: ForgotPasswordComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthLayoutRoutingModule { }

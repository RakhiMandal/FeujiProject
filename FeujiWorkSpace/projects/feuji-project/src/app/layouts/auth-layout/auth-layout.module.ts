import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthLayoutRoutingModule } from './auth-layout-routing.module';
import { LoginLayoutComponent } from '../../pages/loginPage/login-layout.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [

  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AuthLayoutRoutingModule
  ],
})
export class AuthLayoutModule { }

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {  FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { SidemenuComponent } from './components/sidemenu/sidemenu.component';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { EmployeeLayoutComponent } from './layouts/employee-layout/employee-layout.component';
import { ManagerLayoutComponent } from './layouts/manager-layout/manager-layout.component';
import { PmoLayoutComponent } from './layouts/pmo-layout/pmo-layout.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { UserService } from '../models/user.service';
import { EmployeeService } from '../models/employee.service';
import { CommonModule, DatePipe } from '@angular/common';
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import { LoginLayoutComponent } from './pages/loginPage/login-layout.component';



import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

import { TimesheetHomeService } from '../models/timesheetHomeService.service';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatHeaderCell, MatRow, MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatRadioModule } from '@angular/material/radio';
import { MatCardModule } from '@angular/material/card';
import { MatTabsModule } from '@angular/material/tabs';
import { AuthGuard } from './Auth/AuthGuard';
import { AuthInterceptor } from './Auth/AuthInterceptor';




@NgModule({
  declarations: [
  AppComponent,
    NavbarComponent,
    FooterComponent,
    SidemenuComponent,
    AdminLayoutComponent,
    EmployeeLayoutComponent,
    ManagerLayoutComponent,
    PmoLayoutComponent,
    AuthLayoutComponent,
    LoginLayoutComponent,
    ForgotPasswordComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    CommonModule,
    MatListModule,
    MatIconModule,
    MatMenuModule,
    MatCardModule,
    MatTabsModule,
    MatExpansionModule,
    MatTableModule,
    MatHeaderCell,
    MatRow,
    MatTooltipModule,
    MatRadioModule
  ],

  providers: [UserService,TimesheetHomeService,
  EmployeeService,DatePipe, provideAnimationsAsync(), provideAnimationsAsync('noop'),
AuthGuard,{provide:HTTP_INTERCEPTORS,useClass:AuthInterceptor,multi:true}],
  bootstrap: [AppComponent],


})
export class AppModule { }

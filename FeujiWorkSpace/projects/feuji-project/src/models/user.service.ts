import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, tap } from "rxjs";
import { User } from "./user.model";
import { Router } from "@angular/router";
import { Employee } from "./employee.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8082/api';

  constructor(private http: HttpClient,private router: Router) {}

  // login(email: string, password: string):Observable<any>{
  //   const body = { email, password };
  //   return this.http.post(`${this.apiUrl}/users/login`,body)
  // }
login(email: string, password: string):Observable<any>{
    const body = { email, password };
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' }).append('No-AUTH', 'True'); // Append authentication headers
   
    return this.http.post(`${this.apiUrl}/users/login`,body,{ headers: headers })   .pipe(
           tap(data => console.log("login", data)) // Logging the response data
          ); 
  }
 
  logout() {
    this.router.navigate(['/login']);
  }

  // login(body:any):Observable<any>{
  //   return this.http.post(${this.apiUrl}/login,body)
  // }
  // logout() {
  //   this.router.navigate(['/login']);
  
  // getEmployeeByid(userEmpid:any): Observable<Employee> {
  //   const url = ${this.apiUrl}/employee/${userEmpid};
  //   console.log("Request URL:", url);
  //   console.log(userEmpid+"service employee id");
  //   return this.http.get<Employee>(url);
  // }

  getEmployeeByid(userEmpid:any): Observable<Employee> {
    const url = `${this.apiUrl}/employee/${userEmpid}`;
    console.log("Request URL:", url);
    console.log(userEmpid+"service employee id");
    return this.http.get<Employee>(url);
  }
  forgotPassword(userEmail: string, userPassword: string) {
    return this.http.put<any>(`${this.apiUrl}/forgot-password`, { userEmail, userPassword }).pipe(
      tap(response => console.log('HTTP Response Body:', response)),
      catchError(error => {
        console.error('HTTP Error:', error);
        throw error;
      })
    );
  }
// ===============================================
  checkEmailExist(email: string): Observable<boolean> {
    return this.http.post<boolean>(`${this.apiUrl}/check-email`, { email }).pipe(
      tap(exists => console.log('Email Exist:', exists)),
      catchError(error => {
        console.error('Check Email Error:', error);
        throw error;
      })
    );
  }

  generateOTP(email: string): Observable<boolean> {
    return this.http.post<boolean>(`${this.apiUrl}/generate-otp`, { email }).pipe(
      tap(success => console.log('Generate OTP Success:', success)),
      catchError(error => {
        console.error('Generate OTP Error:', error);
        throw error;
      })
    );
  }

  verifyOTP(email: string, otp: string): Observable<boolean> {
    return this.http.post<boolean>(`${this.apiUrl}/verify-otp`, { email, otp }).pipe(
      tap(success => console.log('Verify OTP Success:', success)),
      catchError(error => {
        console.error('Verify OTP Error:', error);
        throw error;
      })
    );
  }

  updatePassword(email: string, newPassword: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/update-password`, { email, newPassword }).pipe(
      tap(() => console.log('Password updated successfully')),
      catchError(error => {
        console.error('Update Password Error:', error);
        throw error;
      })
    );
  }


}


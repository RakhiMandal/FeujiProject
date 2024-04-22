import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs';
import { Observable } from 'rxjs/internal/Observable';
// import { PatientBillingEntity } from '../../Model/billing.model';
// import { BillingDto } from '../../Model/billingDto';
// import { AuthService } from './AuthService';

@Injectable({
  providedIn: 'root'
})
export class RoomtypeDetailsService {
  
  
  constructor(private http: HttpClient) { }

  // // registrationDetails(data: any): Observable<any> {
  // //   const headers = new HttpHeaders({ 'Content-Type': 'application/json' }).append('No-AUTH', 'True'); // Append authentication headers
  // //   return this.http.post<PatientBillingEntity>('http://localhost:8101/admin-service/register/save', data, { headers }).pipe(tap(data => JSON.stringify("Registrationservice" + data)))
  // // }

  // // getAllDetails(pageSize: number, currentPage: number): Observable<BillingDto[]> {
  // //   const params = new HttpParams()
  // //     .set('_page', currentPage.toString())
  // //     .set('_limit', pageSize.toString());
  // //   return this.http.get<BillingDto[]>('http://localhost:8102/patient-billing-service/patientbilling/get', { params })
  // // }
  // // private filterurl = 'http://localhost:8102/patient-billing-service/patientbilling/filter';


  // // filterDataByDateRange(pageSize: number, currentPage: number, startDate: string, endDate?: string): Observable<BillingDto[]> {
  // //   const params = new HttpParams()
  // //     .set('_page', currentPage.toString())
  // //     .set('_limit', pageSize.toString());
  // //   endDate = endDate || new Date().toISOString().split('T')[0];

  // //   const url = `${this.filterurl}?startDate=${startDate}&endDate=${endDate || ''}`;
  // //   return this.http.get<BillingDto[]>(url, { params });
  // // }
  // // saveBillingdetails(details: PatientBillingEntity): Observable<PatientBillingEntity> {
  // //   const headers = { 'Content-Type': 'application/json' };
  // //   return this.http.post<PatientBillingEntity>('http://localhost:8102/patient-billing-service/patientbilling/save', details, { headers }).pipe(tap(data => JSON.stringify(data)))
  // // }
  // storeBillingDetails(data: any): Observable<any[]> {
  //   const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  //   return this.http.put<any[]>('http://localhost:8102/patient-billing-service/patientbilling/update', data, { headers });

  // }
  // billingDetailsById(billId: any[]): Observable<any[]> {
  //   return this.http.get<any[]>(`http://localhost:8082/patient-billing-service/patientbilling/fetch/${billId}`)
  // }

  // loginDetails(data: any): Observable<any> {
  //   const headers = new HttpHeaders({ 'Content-Type': 'application/json' }).append('No-AUTH', 'True'); // Append authentication headers
  //   return this.http.post<any[]>('http://localhost:8101/admin-service/register/login', data, { headers: headers })
  //     .pipe(
  //       tap(data => console.log("login", data)) // Logging the response data
  //     ); 
  // }
  // private apiUrl = 'http://localhost:8082/api';

  // login(email: string, password: string):Observable<any>{
  //   const body = { email, password };
  //   const headers = new HttpHeaders({ 'Content-Type': 'application/json' }).append('No-AUTH', 'True'); // Append authentication headers
   
  //   return this.http.post(`${this.apiUrl}/users/login`,body,{ headers: headers })   .pipe(
  //          tap(data => console.log("login", data)) // Logging the response data
  //         ); 
  // }

  // verifyOtp(email: string, enteredOtp: string): Observable<any[]> {
  //   const params = new HttpParams()
  //     .set('email', email)
  //     .set('enteredOtp', enteredOtp);

  //   const headers = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' }).append('No-AUTH', 'True');

  //   return this.http.post<any[]>(`http://localhost:8101/admin-service/register/verify`, null, { headers, params });
  // }
  // sendOtp(email: string): Observable<any[]> {
  //   return this.http.get<any>(`http://localhost:8101/admin-service/register/generateOtp?email=${email}`);

  // }
  // password(email: string, password: string) {
  //   const params = new HttpParams()
  //     .set('email', email)
  //     .set('password', password);
  //   return this.http.put<any>(`http://localhost:8101/admin-service/register/password`, {}, { params });
  // }
  // getNumber(patientNumber: string): Observable<any> {
  //   const params = new HttpParams()
  //     .set('patientNumber', patientNumber);

  //   const headers = { 'Content-Type': 'application/json' };


  //   return this.http.get<any>(`http://localhost:8103/patient-billing-service/patientbilling/getno?patientNumber=${patientNumber}`)

  // }

}
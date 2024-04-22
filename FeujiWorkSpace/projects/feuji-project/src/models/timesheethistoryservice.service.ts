import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TimesheethistoryserviceService {

  constructor(private http: HttpClient
    ) {}


    taskHistoryUrl="http://localhost:8084/api/timesheet/gettimeSheetHistory"
  accounturl="http://localhost:8083/api/AccountProjectResourceMapping/accountdetails"
  dayaHistory="http://localhost:8084/api/timesheetday"


  fetchData(month: string, year: number,accountName:string,userEmpId:number): Observable<any[]> {
    const url = `${this.taskHistoryUrl}/${month}/${year}/${accountName}/${userEmpId}`;
    return this.http.get<any[]>(url);
  }
  fetchAllData( year: number,accountName:string,userEmpId:number): Observable<any[]> {
    const url = `${this.taskHistoryUrl}/${year}/${accountName}/${userEmpId}`;
    return this.http.get<any[]>(url);
  }
  fetchAccountBymonthAndYear(month: string,year: number,userEmpId:number): Observable<any[]> {
    const url = `${this.taskHistoryUrl}/getAccountByMonthAndYear/${month}/${year}/${userEmpId}`;
    return this.http.get<any[]>(url);
  }
  fetchYear(employeeId:number): Observable<any[]> {
    const url = `${this.taskHistoryUrl}/getYears/${employeeId}`;
    return this.http.get<any[]>(url);
  }

  public getAccount(employeeId:number):Observable<any[]>
  {

    const url1=`${this.accounturl}/${employeeId}`;
    return this.http.get<any[]>(url1)
  }

  getDayHistory(uuId:string): Observable<any[]> {
    const url = `${this.dayaHistory}/gettimeSheetDayHistory/${uuId}`;
    return this.http.get<any[]>(url); 
  }


}

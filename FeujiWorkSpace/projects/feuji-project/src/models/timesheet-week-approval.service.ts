import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { timesheetWeekApproval } from './timesheet-week-approval.model';

@Injectable({
  providedIn: 'root'
})
export class TimesheetWeekApprovalService {
  private accurl = 'http://localhost:8084/api/timesheet/getaccountdetails';
  private apiUrl = 'http://localhost:8084/api/TimesheetWeekSummaryView';
  private accountUrl = 'http://localhost:8084/api/timesheet'
  private weekTimeSheet: timesheetWeekApproval[] = [];
  constructor(private http: HttpClient) { }

  getAccounts(empId: number): Observable<any[]> {
    const url = `${this.accurl}?userEmpId=${empId}`;
    return this.http.get<any[]>(url);
  }

  fetchData(month: string, year: number, accountId: number): Observable<any[]> {
    const url = `${this.accountUrl}/gettimeSheetHistory/bymonth/${month}/${year}/${accountId}`;

    return this.http.get<any[]>(url);
  }
  getWeekTimesheets(approvedBy: number, accountId: number, weekNumber: number): Observable<timesheetWeekApproval[]> {
    const url = `${this.apiUrl}/timesheets/manager/${approvedBy}/${accountId}/${weekNumber}`;
    return this.http.get<timesheetWeekApproval[]>(url).pipe(tap(data => this.weekTimeSheet = data))
  }

  getProjects(userEmpId: number, month: string, year: number, accountId: number, employeeId: number): Observable<timesheetWeekApproval[]> {
    const url = `${this.apiUrl}/getTimeSheeApproval/${userEmpId}/${month}/${year}/${accountId}/${employeeId}`;
    return this.http.get<timesheetWeekApproval[]>(url);
  }

  getProjectsByAccountId(accountId: number, employeeId: number): Observable<timesheetWeekApproval[]> {
    const url = `${this.apiUrl}/projects/${accountId}/${employeeId}`;
    return this.http.get<timesheetWeekApproval[]>(url);
  }

  getStoredWeekTimesheets(): timesheetWeekApproval[] {
    return this.weekTimeSheet;
  }


  getTotalHours(employeeId: number, accountProjectId: number, weekNumber: number): Observable<timesheetWeekApproval[]> {
    const url = `${this.apiUrl}/total/${employeeId}/${accountProjectId}/${weekNumber}`;
    return this.http.get<timesheetWeekApproval[]>(url)
  }

// --------------------------------------------------
getAllTimesheets(approvedBy: number): Observable<timesheetWeekApproval[]> {
  const url = `${this.apiUrl}/all?approvedBy=${approvedBy}`;
  return this.http.get<timesheetWeekApproval[]>(url);
}

getAccount(approvedBy: Number): Observable<timesheetWeekApproval[]> {
  return this.http.get<timesheetWeekApproval[]>(`${this.apiUrl}/accounts/${approvedBy}`);
}
}
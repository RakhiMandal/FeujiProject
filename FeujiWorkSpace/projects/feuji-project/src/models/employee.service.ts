import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map } from 'rxjs';
import { Employee, EmployeeSaving } from './employee.model';
import { SaveEmployee } from './saveemployee.model';
import { SharedService } from './shared.service';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  referenceUrl:String
  usersUrl:string
  employee:string

  constructor(private http: HttpClient, private shared : SharedService) {
    this.referenceUrl=this.shared.referenceUrl;
    this.usersUrl=this.shared.userUrl;
    this.employee=this.shared.employeeUrl
  }

  private apiUrl = this.shared.employeeUrl;
  private empUrl ='http://localhost:8082/api/employee';
  private  billurl="http://localhost:8089/api/referencedetails/getref"

  //  Method to Save Employment
  saveEmployee(employeeData: EmployeeSaving): Observable<EmployeeSaving> {
    console.log(employeeData + "service method");
    return this.http.post<EmployeeSaving>(`${this.apiUrl}/employees`, employeeData);
  }

  //  Method to check EmployeCode
  checkEmployeeCodeUnique(employeeCode: string): Observable<boolean> {
    console.log(employeeCode);
    return this.http.get<boolean>(`/check-employee-code/${employeeCode}`);
  }

  //  Method to check unicqe email

  checkEmployeeEmail(employeeEmail: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.empUrl}/checkEmail?email=${employeeEmail}`);
  }
  

  //  Method to check  who are managers
  getReportingManager():Observable<any>{
    return this.http.get<any>(`${this.employee}/reporting-managers`)
  }

// GET BY REFERENCE TYPE ID
getByReferenceTypeId(referenceTypeId: number): Observable<SaveEmployee[]> {
  return this.http.get<SaveEmployee[]>(`${this.apiUrl}/referenceTypeId/${referenceTypeId}`);
}
// GET BY REFERENCE TYPE
getAllReferenceType():Observable<any>{
  return this.http.get<any>(`${this.referenceUrl}/all`)
}

// method to get all employee
getAllEmployees():Observable<any>{
  return this.http.get<any>(`${this.employee}/getAll`)
}

// search api
searchByEmployeeName(firstName: string):Observable<any>{
  return this.http.get<any>(`${this.employee}/search?firstName=${firstName}`)
}


//   // Service method to retrieve employment types
getEmploymentType(referenceTypeId: number): Observable<SaveEmployee[]> {
  return this.http.get<SaveEmployee[]>(`${this.apiUrl}/EmploymentType/${referenceTypeId}`);

}
getEmployeeDetails():Observable<any>{
  return this.http.get<any>(`${this.empUrl}/getEmployeeDetails`)
}


getEmployeeDetailByUuId(uuid: string):Observable<any>{
  return this.http.get<any>(`${this.empUrl}/getEmployeeDetailByUUiD/${uuid}`)
}

public getBusinessUnitType():Observable<any[]>
  {

    return this.http.get<any[]>(`${this.billurl}/Business Unit`);

  }

  public getStatusType():Observable<any[]>
  {
    return this.http.get<any[]>(`${this.billurl}/Account Status`);

  }

getUpdateEmployee(empData:EmployeeSaving):Observable<any>{
  console.log(empData);
  const headers=new HttpHeaders("application/json");


  return this.http.put<any>(`${this.empUrl}/updateEmployee`,empData,{headers})
}
deleteEmployee(employeeId:number):Observable<any>{

  return this.http.delete<any>(`${this.empUrl}/deleteEmp/${employeeId}`)
}
}

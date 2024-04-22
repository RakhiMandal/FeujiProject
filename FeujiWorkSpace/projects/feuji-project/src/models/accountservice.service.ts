import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, tap, throwError } from 'rxjs';
import { Account } from './account.model';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class AccountserviceService {

  private apiUrl = 'http://localhost:8081/api';
 private  billurl="http://localhost:8089/api/referencedetails/getref"

  constructor(private http: HttpClient) {}

  getAccount():Observable<Account[]>{
    console.log("service method")

    return this.http.get<Account[]>(`${this.apiUrl}/accountSave/getAccountDto`);
    // alert();
    }
    getById(id:any):Observable<any[]>{
      // alert(cate);
      return this.http.get<any[]>(`${this.apiUrl}/accountSave/getbyId/${id}`)

    }
    getAccountByUuId(uuId:any):Observable<any[]>{
      // alert(cate);
      return this.http.get<any[]>(`${this.apiUrl}/accountSave/fetchByuuId/${uuId}`)

    }
    saveAccount(accountData: Account): Observable<Account> {
      return this.http.post<Account>(`${this.apiUrl}/accountSave/save`, accountData)
        .pipe(
          tap(() => {
            Swal.fire('Success', 'Account saved successfully.', 'success');
          }),
          catchError((error: HttpErrorResponse) => {
            if (error.status === 409) {
              Swal.fire('Error', 'Account with this name already exists.', 'error');
            } else {
              Swal.fire('Error', 'Error in saving account details: ' + error.message, 'error');
            }
            return throwError('Error in saving account details: ' + error.message);
         })
      );
    }



  updateAccount(accountData:Account): Observable<Account> {
    return this.http.put<Account>(`${this.apiUrl}/accountSave/updateAccount`, accountData);
  }
  getEmployeeName():Observable<any[]>{
    return this.http.get<any[]>(`${this.apiUrl}/accountSave/getEmployee`);
  }


  public getBusinessUnitType():Observable<any[]>
  {

    return this.http.get<any[]>(`${this.billurl}/Business Unit`);

  }

  public getStatusType():Observable<any[]>
  {
    return this.http.get<any[]>(`${this.billurl}/Account Status`);

  }

  deleteEmployee(accountId:number):Observable<any>{

    return this.http.delete<any>(`${this.apiUrl}/accountSave/deleteAcc/${accountId}`)
  }
}

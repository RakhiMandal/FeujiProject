import { Injectable } from "@angular/core";

import { Observable, throwError } from "rxjs"; // Import throwError from RxJS
import { catchError } from 'rxjs/operators'; // Import catchError from RxJS operators
import { Holiday } from "./holiday.model";
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Injectable({providedIn: 'root'})
export class HolidayService {
  public holiday:Holiday[]=[]
  constructor(private http:HttpClient) { }


  private apiUrl='http://localhost:8084/api/holiday';
  getholiday():Observable<Holiday[]>{
   return this.http.get<[Holiday]>(`http://localhost:8084/api/holiday`);

  }
  saveholiday(holiday:Holiday):Observable<Holiday>{
    console.log(holiday)
      return this.http.post<Holiday>(`http://localhost:8084/api/holiday/save`,holiday);
  }
    
  updateHoliday(holiday:Holiday):Observable<String>{
    console.log(holiday);
    return this.http.put(`http://localhost:8084/api/holiday/update`,holiday,{responseType:'text'});
  }
  

  deleteRow(holidayId:any){
    console.log("entered service remove",holidayId);
    const headers=new HttpHeaders({'content-Type':'application/json'});
    return this.http.delete<Holiday>(`${this.apiUrl}/delete/${holidayId}`);
    
  }

  getHolidayByYear( year:number):Observable<any>{
    console.log(year);
    
    return this.http.get<any>(`http://localhost:8084/api/holiday/getHolidayByYear/${year}`)

  }
  
}

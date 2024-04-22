import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Holiday } from "./holiday.model";


@Injectable({
  providedIn: 'root'
})
export class HolidayService {
  public holiday:Holiday[]=[]
  constructor(private http:HttpClient) { }

  getholiday():Observable<Holiday[]>{
   return this.http.get<[Holiday]>(`http://localhost:8084/api/holiday`);

  }
  saveholiday(holiday:Holiday):Observable<Holiday>{
    console.log(holiday)
      return this.http.post<Holiday>(`http://localhost:8084/api/holiday`,holiday);
  }

  deleteHoliday(HolidayId:number){
    console.log(HolidayId);
    return this.http.delete(`http://localhost:8088/api/holiday/delete/${HolidayId}`);

  }
}
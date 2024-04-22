import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

   employeeUrl = 'http://localhost:8082/api/employee';
   userUrl= 'http://localhost:8082/api/users';
   accountProjectUrl='http://localhost:8083/api/accountProjects';
   holidayUrl='http://localhost:8084/api/holiday';
   accountServiceUrl='http://localhost:8081/api/accountSave';
   referenceUrl= 'http://localhost:8089/api/referencetype';

  constructor() { }
}

import { Injectable } from "@angular/core";
import { Employee } from "./employee.model";
import { EmployeeService } from "./employee.service";

@Injectable({providedIn: 'root'})
export class EmployeeRepo{

  public employee:Employee[]=[];

  constructor(private dataSource:EmployeeService) {
     // this.getEmployees();
   }

  // getEmployees(){
  //    this.dataSource.getEmployees().subscribe(data=>{
  //     this.employee=data;
  //   })
  // }

  getEmployeeDetails():Employee[]{
    return this.employee;
  }

  sendEmployee(emp:Employee){
    this.dataSource.saveEmployee(emp).subscribe(
      (data)=>{
       console.log(data);
       this.resetForm();
     },
    (error)=>{
     console.error("error no data found");
    }
    )
  }

  resetForm(): void {
    // Implement code to reset form fields
  }



}


import { Injectable } from "@angular/core";
import { User } from "./user.model";
import { UserService } from "./user.service";
import { Employee } from "./employee.model";
@Injectable({providedIn: 'root'})
export class UserRepo
{
  empDataById: any;
// constructor(private userService:UserService){};
//   public loggedInuserData:any;
//   getUserData(data:any){
//     console.log(data);
//     this.loggedInuserData=data;
//     console.log(this.loggedInuserData)
//     console.log(this.loggedInuserData.userEmpId)
//   }
//    getEmployeeById(){
//     this.userService.getEmployeeByid(this.loggedInuserData.empId)
//    }

constructor(private userService: UserService) {}

  public loggedInuserData: any;
  empId: Employee = new Employee(0,'','','','','','','','',new Date(),0,'','',0,'',new Date(),'','',0,new Date(),'',new Date(),0,'');

  getUserData(data: any) {
    console.log(data);
    this.loggedInuserData = data;
    console.log(this.loggedInuserData);
    console.log(this.loggedInuserData.userEmpId);
    this.getEmployeeById();
  }

  getEmployeeById() {
    // Assuming loggedInuserData contains userEmpId
    const userEmpId = this.loggedInuserData.userEmpId;

    // Ensure userEmpId is valid before making the request
    if (userEmpId) {
      this.userService.getEmployeeByid(userEmpId).subscribe(
        (result:any) => {
          // console.log('Employee Details:', result);
          this.empDataById=result[0];
          console.log("this.empId DATA",this.empDataById);

          // You can do something with the employee details here
        },
        (error) => {
          console.error('Error fetching employee details', error);
        }
      );
    } else {
      console.error('Invalid userEmpId');
    }
  }
}
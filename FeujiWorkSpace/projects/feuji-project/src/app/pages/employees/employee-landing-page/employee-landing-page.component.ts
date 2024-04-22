import { Component } from '@angular/core';

@Component({
  selector: 'app-employee-landing-page',
  templateUrl: './employee-landing-page.component.html',
  styleUrl: './employee-landing-page.component.css'
})
export class EmployeeLandingPageComponent {

  public empName:string='';
  public user:any;

  ngOnInit(){
    const user = localStorage.getItem("user");
    if (user) {
      this.user = JSON.parse(user);
      this.empName=this.user.firstName
      console.log(this.empName);
      console.log(user);


    }
  }

}

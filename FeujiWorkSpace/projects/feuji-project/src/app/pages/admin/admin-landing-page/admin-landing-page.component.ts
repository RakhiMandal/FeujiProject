import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-landing-page',
  templateUrl: './admin-landing-page.component.html',
  styleUrl: './admin-landing-page.component.css'
})
export class AdminLandingPageComponent {


  public empName:string='';
  public user:any;

  ngOnInit(){
    console.log("ngOnInit called");
    const user = localStorage.getItem("user");
    if (user) {
      this.user = JSON.parse(user);
      console.log("User from localStorage:", this.user);
      this.empName=this.user.firstName
      console.log(this.empName);
      console.log(user);
    }
  }

}

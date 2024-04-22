import { Component } from '@angular/core';

@Component({
  selector: 'app-manager-landing-page',
  templateUrl: './manager-landing-page.component.html',
  styleUrl: './manager-landing-page.component.css'
})
export class ManagerLandingPageComponent {
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

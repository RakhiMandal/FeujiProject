import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-managerdashboard',
  templateUrl: './managerdashboard.component.html',
  styleUrl: './managerdashboard.component.css'
})
export class ManagerdashboardComponent implements OnInit{


  public empName:string='';
  public user:any;

  ngOnInit(){
    const user = localStorage.getItem("user");
    if (user) {
      this.user = JSON.parse(user);
      this.empName=this.user.userName
    }
  }
}

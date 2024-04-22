import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit {

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

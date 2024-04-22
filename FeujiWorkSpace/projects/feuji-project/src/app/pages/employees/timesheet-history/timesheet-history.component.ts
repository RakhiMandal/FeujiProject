import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { Router } from '@angular/router';
import { TimesheetDayhistoryComponent } from '../timesheet-dayhistory/timesheet-dayhistory.component';
import { TimesheethistoryserviceService } from '../../../../models/timesheethistoryservice.service';
@Component({
  selector: 'app-timesheet-history',
  templateUrl: './timesheet-history.component.html',
  styleUrl: './timesheet-history.component.css'
})
export class TimesheetHistoryComponent implements OnInit{

 public timesheetData: any[]=[];
 public account:any[]=[];
  public months: string[] = ['All','January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
  public years: number[] = []; // Example years, adjust as needed
  public accountName:string[]=[];

 public selectedMonth: string;
 public selectedYear: number;
public selectedAccountName:string='';
currentUser:number=0;
 ngOnInit(): void {

  const userStr = localStorage.getItem('user');

  if (userStr !== null) {
      const userData = JSON.parse(userStr);
      const userEmpId = userData.userEmpId;

      this.currentUser=userEmpId;
      // alert(this.currentUser)
  } else {
      // Handle the case when 'user' key is not found in localStorage or is null
      console.error('User data not found in localStorage');
  }
  this.fetchData();
  // this.populateYears();
  this.getYears();
  this.getAccount();
  this.getAccountBymonthAndYear();
 }
 constructor(private timesheetService: TimesheethistoryserviceService,private router: Router,private dialog: MatDialog ) {
  // Set default values for month and year
  const currentDate = new Date();
  this.selectedMonth = this.months[currentDate.getMonth()+1];
  this.selectedYear = currentDate.getFullYear();
this.getAccountBymonthAndYear();
}

getAccountBymonthAndYear(): void {
  // Make a request to the backend to fetch the account name based on the selected month
  console.log(this.selectedMonth + " " + this.selectedYear);

  this.timesheetService.fetchAccountBymonthAndYear(this.selectedMonth, this.selectedYear,this.currentUser)
    .subscribe(acc => {
      console.log("asdfghjklkjhgfdsdfghjk"+acc);

      // Assigning the selected account name to the first account name in the array
      this.selectedAccountName = acc[0].accountName;
      console.log("asdfghjklkjhgfdsdfghjk"+this.selectedAccountName);
      // Fetch data after getting the account name
       this.fetchData();
    });
}


fetchData(): void {
  console.log(
    this.selectedAccountName
  )
  console.log(this.selectedYear);
  console.log(this.currentUser);
  if (this.selectedMonth === 'All') {
      this.timesheetService.fetchAllData( this.selectedYear,this.selectedAccountName,this.currentUser)
    .subscribe(data => {
      this.timesheetData = data;
      console.log(data);

    });
    console.log(
      this.selectedAccountName
    )
    }else {
  this.timesheetService.fetchData(this.selectedMonth, this.selectedYear,this.selectedAccountName,this.currentUser)
    .subscribe(data => {
      this.timesheetData = data;
      console.log(data);

    });
    console.log(
      this.selectedAccountName
    )
    }
}
getYears(){
  this.timesheetService.fetchYear(this.currentUser).subscribe(data=>{
   console.log(data);
   this.years=data;
   console.log(this.years);
 })

}
getAccount(){

  this.timesheetService.getAccount(this.currentUser).subscribe(data=>{
   console.log(data);
   this.account=data;
   console.log(this.account);
 })

}

openPopup(uuId: string, projectName: string, status: string,empFirstName:string,
empLastName:string,weekStartDate:string,weekEndDate:string): void {
  const dialogRef = this.dialog.open(TimesheetDayhistoryComponent, {
    // width: '60%',
    // height: '60%',
    data: { uuId, projectName, status ,empFirstName,empLastName,weekStartDate,weekEndDate} // Pass the UUID, projectName, and status as data to the dialog
  ,panelClass: 'dialog-with-margin'
  });
}
}



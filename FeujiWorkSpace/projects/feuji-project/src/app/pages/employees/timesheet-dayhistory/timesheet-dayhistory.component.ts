import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DatePipe } from "@angular/common";
import { TimesheethistoryserviceService } from '../../../../models/timesheethistoryservice.service';

@Component({
  selector: 'app-timesheet-dayhistory',
  templateUrl: './timesheet-dayhistory.component.html',
  styleUrl: './timesheet-dayhistory.component.css'
})
export class TimesheetDayhistoryComponent implements OnInit {
  constructor(private ref:ActivatedRoute,private timesheetService: TimesheethistoryserviceService,
   public dialogRef: MatDialogRef<TimesheetDayhistoryComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ){}
  public uuId: string = "";
public projectName: string = "";
public status: string = "";
public timesheetData:any=[];
public empFirstName:string="";
public empLastName:string="";
public weekStartDate:string="";
public weekEndDate:string="";
ngOnInit(): void {
  this.uuId = this.data.uuId;
  this.projectName = this.data.projectName;
  this.status = this.data.status;
  this.empFirstName=this.data.empFirstName;
  this.empLastName=this.data.empLastName;
  this.weekStartDate=this.data.weekStartDate;
  this.weekEndDate=this.data.weekEndDate;
  this.getDayHistory(this.uuId);
  console.log(this.uuId);
}
  closePopup(): void {
    this.dialogRef.close();
  }

  getDayHistory(uuId:string){
    this.timesheetService.getDayHistory(this.data.uuId).subscribe(
    (items)=>{
    this.timesheetData=items;
    console.log(items);

    }

  )
}
}

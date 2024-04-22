import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountprojectService } from '../../../../models/accountproject.service';
import { NgModel } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-update-project',
  templateUrl: './update-project.component.html',
  styleUrl: './update-project.component.css'
})
export class UpdateProjectComponent implements OnInit {
  public accountProjectUuid:string="";
  public accountProject:any;
  public accountProjectdetails:any=AccountprojectService;
  accountList: any[] = [];
  employeeList: any[] = [];
  priorityTypes: any[] = [];
  statusTypes:any[]=[];

    constructor( private router: Router,private ref:ActivatedRoute,private accountProjectService :AccountprojectService){}
    
  
      ngOnInit(): void {
        this.accountProjectUuid = history.state.project.uuid;
        console.log( this.accountProjectUuid);
        
  this.send( this.accountProjectUuid );

  this.getAccountNames();
    this.getEmployeeName();
    this.onSelectPriority();
    this.onSelectStatus();
  }
  
  send(accountprojectuuid:string){
    this.accountProjectService.getAccountProjectByUuid(accountprojectuuid).subscribe(
    (items)=>{
    this.accountProject=items[0];
    console.log(items);
  
    }
  
  )
  }

  updateAccountProject(uuid:any,isDeleted:boolean) {
    this.accountProject.uuId=uuid;
    console.log(isDeleted);
    

    this.accountProject.isDeleted=isDeleted;

    console.log(this.accountProject);
    this.accountProjectService.updateAccountProject(this.accountProject).subscribe({
      next: (res) => {
        this.accountProjectdetails = res;
        // alert(this.accountProject)
        // alert(this.accountProjectdetails);
        console.log(this.accountProjectdetails);
        
        Swal.fire('Success', 'Data updated', 'success');
      },
      error: (error) => {
        console.error('There was an error!', error);
        Swal.fire('Error', 'Failed to update data: ' + error.message, 'error');
      }
    });
}

getAccountNames(): void {

  this.accountProjectService.getAccountName().subscribe(
    (data) => {
      this.accountList = data;
      // alert(this.accountList);
    },
    error => {
      console.error('Error occurred while fetching account names:', error);
    }
  );
}

getEmployeeName(): void {

  this.accountProjectService.getEmployeeName().subscribe(
    (data) => {
      this.employeeList = data;
    },
    error => {
      console.error('Error occurred while fetching account names:', error);
    }
  );
}

onSelectPriority() {
  this.accountProjectService.getPriorityType().subscribe((priorities:any[]) => {
    console.log(priorities);
    this.priorityTypes = priorities;
  });
}
onSelectStatus() {
  this.accountProjectService.getStatusType().subscribe((status:any[]) => {
    console.log(status);
    this.statusTypes = status;
  });
}

}

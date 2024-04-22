import { Injectable } from "@angular/core";
import { Accountproject } from "./accountproject.model";
import { AccountprojectService } from "./accountproject.service";




@Injectable({providedIn: 'root'})
export class AccountprojectRepo{

  public accountProject:Accountproject[]=[];

  constructor(private dataSource:AccountprojectService,private accountproject:Accountproject) {
      this.getAccountProjects();
   }

  getAccountProjects(){
     this.dataSource.getAccountProjects(accountproject.getById).subscribe(data=>{
      this.accountProject=data;
    })
  }

  getAccountProjectDetails():Accountproject[]{
    return this.accountProject;
  }

  sendAccountProject(project:Accountproject){
    this.dataSource.saveAccountProject(project).subscribe(
      (data)=>{
       console.log(data);
      //  this.resetForm();
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


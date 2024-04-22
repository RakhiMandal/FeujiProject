import { Component, OnInit } from '@angular/core';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import { MatDialogRef } from '@angular/material/dialog';
import { SkillCategoryBean } from '../../../../models/SkillCategoryBean.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-skill-category',
  templateUrl: './add-skill-category.component.html',
  styleUrls: ['./add-skill-category.component.css']
})
export class AddSkillCategoryComponent implements OnInit {
  newSkillCategoryName: any;
  referenceTypeId: number = 0;
  accordionData: any[] = [];
  skillCategory:string='Skill Category';
  constructor(private apiService: EmployeeSkillService,
    public dialogRef: MatDialogRef<AddSkillCategoryComponent> ,
    private employeeSkillService: EmployeeSkillService) { }

  ngOnInit(): void {
    this.fetchSkillCategories();
    this.fetchSkillCategoryId();
  }

  close(): void {
    this.dialogRef.close();
  }

  fetchSkillCategories(): void {
    this.apiService.getSkillCategoryNames().subscribe((data: any[]) => {
      this.accordionData = data;
    });
  }

  fetchSkillCategoryId()
  {
    this.employeeSkillService.getSkillCategoryIdByName(this.skillCategory).subscribe(response=>{
      this.referenceTypeId=response.referenceTypeId
    });
  }
  saveSkillCategory(): void {
    this.newSkillCategoryName = this.newSkillCategoryName.replace(/\s+/g, ' ');
    this.newSkillCategoryName = this.newSkillCategoryName.trim();
   
    

      const skillCategoryBean: SkillCategoryBean = new SkillCategoryBean(
        this.newSkillCategoryName,
        { referenceTypeId: this.referenceTypeId, referenceTypeName: this.newSkillCategoryName },
        0,
        'Admin',
        'Admin'
      );


      this.apiService.saveSkillCategoryAdmin(skillCategoryBean).subscribe(
        response => {
          this.fetchSkillCategories();
          Swal.fire({ title: "Skill category saved successfully", icon: "success" });
          this.dialogRef.close();
        },
        error => {
          Swal.fire({ title: "Skill Category already Exist", icon: "error" });
          this.dialogRef.close();
        }
      );
    }
  

}

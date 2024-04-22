import { Component, Inject, OnInit } from '@angular/core';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import { SubSkillCategoryBean } from '../../../../models/SubSkillCategoryBean.model';
import Swal from 'sweetalert2';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-add-sub-skill-category',
  templateUrl: './add-sub-skill-category.component.html',
  styleUrl: './add-sub-skill-category.component.css'
})
export class AddSubSkillCategoryComponent implements OnInit {
  accordionData: any[] = []; 
  accordionSubData: any[] = []; 
  panelOpenState: boolean = false;
  selectedSkillCategory: any;
  newSkillCategoryName: any;
  referenceTypeId: any;
  referenceTypeRecord:any;
  newSubSkillCategoryName: string = '';

  selectedsubskillcategory: string = '';

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<AddSubSkillCategoryComponent>,
    private employeeSkillService: EmployeeSkillService
  ) { }

  ngOnInit(): void {
    this.selectedsubskillcategory = this.data.selectedsubskillcategory;
  }
  close(selectedCategory: any): void {
    this.dialogRef.close(selectedCategory);
  }


  saveSubSkillCategory() {
    this.newSubSkillCategoryName=this.newSubSkillCategoryName.replace(/\s+/g, ' ');
    this.newSubSkillCategoryName = this.newSubSkillCategoryName.trim();
    this.employeeSkillService.getSkillCategoryTypeId(this.selectedsubskillcategory)
      .subscribe(response => {
        this.referenceTypeRecord = response
    console.log( this.referenceTypeRecord)
        if (this.newSubSkillCategoryName.trim() !== '') {
          const subskillCategoryBean: SubSkillCategoryBean = new SubSkillCategoryBean(
            this.newSubSkillCategoryName,
            {
              referenceTypeId: this.referenceTypeRecord.referenceTypeId,
              referenceTypeName: this.newSubSkillCategoryName
              
            },
            "Admin",
            "Admin"
          );

  console.log("before save"+subskillCategoryBean.referenceTypeId.referenceTypeId)
          this.employeeSkillService.saveSubSkillCategoryAdmin(subskillCategoryBean).subscribe(
            response => {
              Swal.fire({
                title: "Sub Skill category saved successfully",
                icon: "success"
              })
              this.dialogRef.close(this.selectedCategory);
            },
            error => {
              Swal.fire({
                title: "Sub Skill already present",
                icon: "error"
              })
              this.dialogRef.close();
            }
          );
        } else {
          console.log('New sub skill category name is empty or not defined');
        }
      });
  }
  


  selectedCategory(selectedCategory: any) {
    throw new Error('Method not implemented.');
  }


}

import { Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import { MatDialog } from '@angular/material/dialog';
import { AddSkillCategoryComponent } from '../add-skill-category/add-skill-category.component';
import { AddSubSkillCategoryComponent } from '../add-sub-skill-category/add-sub-skill-category.component';
import { SubSkillData } from '../../../../models/SubSkillData.service';
import { SkillDisplayComponent } from '../../skillgap/skill-display/skill-display.component';
import { SkillData } from '../../../../models/SkillData.service';
import Swal from 'sweetalert2';
import { ConfirmDialogComponent } from '../../skillgap/confirm-dialog/confirm-dialog.component';
@Component({
  selector: 'app-add-main-skill',
  templateUrl: './add-main-skill.component.html',
  styleUrls: ['./add-main-skill.component.css'],
})
export class AddMainSkillComponent implements OnInit {

  anyPanelExpanded: boolean = false;
  enableCount()
  {
    this.anyPanelExpanded=true;
  }
  accordionData: any[] = []; 
  accordionSubData: any[] = []; 
  panelOpenState: boolean = false;
  selectedTechCat: any;
  allSkills: any[] = [];
  selectedSubItem: any;
  size: number = 0;
  selectedStatus: number = 0;
  selectedSkillId: number = 0;
  skillIds: any[] = [];
  status: any[] = [];
  changesToSave: any[] = [];
  getTechnicalCategory: any;
  selectedSkillCategory: string = '';
  selectedSubSkillCategory: number = 0;
  
  constructor(private employeeSkillService: EmployeeSkillService, 
    public dialog: MatDialog, private subSkillDataSevice: SubSkillData, 
    private skillDataSevice: SkillData) {
  }
  ngOnInit(): void {
    this.loadSkillCategories();
  }

  loadSkillCategories() {
    this.employeeSkillService.getSkillCategories('Skill Category').subscribe((data: any[]) => {
      this.accordionData = data;
      
    });
  }

  onSelectSkillCategory(selectedSkillCategory: any) {
    this.size=0;
    this.accordionSubData = [];
    this.selectedSkillCategory = selectedSkillCategory;

    this.employeeSkillService.getTechnicalCategory(selectedSkillCategory).subscribe((subSkills: any[]) => {
      this.accordionSubData = subSkills;
      this.subSkillDataSevice.updateAccordionSubData(subSkills);
    },
      (error) => {
        console.error(error);
      });
  }
  onSelectTechCat(techCat: any) {
    this.selectedSubSkillCategory = techCat;
    this.size = 0;
    this.selectedSubItem = this.accordionSubData.find(item => item.referenceDetailId === techCat);

    this.employeeSkillService.getSkills(techCat).subscribe((skills: any[]) => {
      this.allSkills = skills;

      this.skillDataSevice.updateAccordionSubData(skills);
      this.calculateSize();
    },
      (error) => {
        console.error(error);
      }
    )
  }
  onSelectSkill(skillId: any) {
    this.selectedSkillId = skillId

  }
  calculateSize() {
    this.size = this.allSkills.length;
  }
  addNewRow(): void {
    this.size=0;
    const dialogRef = this.dialog.open(AddSkillCategoryComponent, {
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadSkillCategories();

    });
  }
  addNewSubSkill(selectedsubskillcategory: string): void {
    this.size=0;
    const dialogRef = this.dialog.open(AddSubSkillCategoryComponent, {
      panelClass: 'dialog-background',
      data: { selectedsubskillcategory: selectedsubskillcategory } 
    });

    dialogRef.afterClosed().subscribe(result => {
      this.onSelectSkillCategory(selectedsubskillcategory);
    });
  }




  addNewSkill(selectedSubSkillCategory: number, selectedSkillCategory: any): void {
    const dialogRef = this.dialog.open(SkillDisplayComponent, {
      panelClass: 'dialog-background',
      data: {
        selectedSubSkillCategory: selectedSubSkillCategory,
        selectedSkillCategory: selectedSkillCategory
      } 
      
    });
    
    dialogRef.afterClosed().subscribe(result => {
      this.onSelectTechCat(this.selectedSubSkillCategory);
    });
  }

  updateStatus(item: any) {
    if (!this.changesToSave.includes(item)) {
      this.changesToSave.push(item);
    }
  }

  saveChanges() {
    if (this.changesToSave.length === 0) {
      Swal.fire("No changes to save");
      return;
    }
    const skillIds = this.changesToSave.map(item => item.skillId);
    const status = this.changesToSave.map(item => item.status ? 1 : 0);
    this.employeeSkillService.updateStatusAdmin(skillIds, status).subscribe(
      (response: any) => {
        this.onSelectTechCat(this.selectedSubSkillCategory);
        Swal.fire({
          title: 'Status',
          text: 'Status updated successfully',
          icon: 'success',
          confirmButtonText: 'Ok',
        });
        this.changesToSave = [];
      },
      (error: any) => {
        Swal.fire({
          title: 'Status',
          text: 'Unable to Update Status',
          icon: 'error',
          confirmButtonText: 'Ok',
        });
      }
    );
  }

  cancelChanges() {
    if (this.changesToSave.length === 0) {
      Swal.fire("Nothing to cancel");
      return;
    }
    this.changesToSave.forEach((item) => {
      item.status = !item.status;
      Swal.fire("Cancel the changes done");
    });
    this.changesToSave = []; 
  }

  deleteSubCategory(subItemreferenceDetailId:any)
  {
    this.employeeSkillService.deleteSubCategory(subItemreferenceDetailId).subscribe((res: any) => {
      const response = res;
      this.onSelectSkillCategory(this.selectedSkillCategory);
    })
   
  }

  openConfirmationDialogSubCategory(subItemreferenceDetailId:number) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: { title: 'Confirm Deletion', message: 'Are you sure you want to delete?' }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleteSubCategory(subItemreferenceDetailId)
        Swal.fire({
          title: ' Deleted Successfully',
          icon: 'success',
          confirmButtonText: 'Ok',
        });
        this.onSelectSkillCategory(this.selectedSkillCategory)
      }
      
    });
  }

  openConfirmationDialogForCategory(categoryName:string)
  {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: { title: 'Confirm Deletion', message: 'Are you sure you want to delete?' }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleteCategory(categoryName)
        Swal.fire({
          title: ' Deleted Successfully',
          icon: 'success',
          confirmButtonText: 'Ok',
        });
        this.loadSkillCategories();

      }
    });
  }

  deleteCategory(categoryName:string)
  {
    this.employeeSkillService.deleteCategory(categoryName).subscribe((res: any) => {
      const response = res;
     this.loadSkillCategories();
    })
  }
}

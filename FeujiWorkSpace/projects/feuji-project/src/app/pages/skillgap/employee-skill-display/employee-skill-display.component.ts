import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { EmployeeUiBean } from '../../../../models/EmployeeSkill.model';
import { EmployeeSkillGet } from '../../../../models/EmployeeSkillGet.model';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-employee-skill-display',
  templateUrl: './employee-skill-display.component.html',
  styleUrl: './employee-skill-display.component.css',
})

export class EmployeeSkillDisplayComponent implements OnInit {
  skillType: any;
  oneEmployeeSkill: any;
  constructor(private http: HttpClient, private empskillService: EmployeeSkillService, private dialog: MatDialog) { }
  empMail: string = '';
  public employeeSkill: EmployeeUiBean = new EmployeeUiBean(this.empMail, "", '', '', '', '', '', '', '', '', '0')
  employeesSkillGet: EmployeeSkillGet[] = [];
  skillCategories: any[] = [];
  technicalcategories: any[] = [];
  skills: any[] = [];
  skillCatogoryInput: string = 'Skill Category'
  skillTypeInput = 'Skill Type';
  skillCompetencyInput = "Skill Competency"
  skillTypes: any[] = [];
  skillTypes1: any[] = [];
  skillCompetencies: any[] = [];
  skillCompetencies1: any[] = [];
  editedRow: any;
  skillCategory: string = '';
  technicalCategory: string = '';
  showNewRow: boolean = false;
  newRows: any[] = [];
  isEditModee: boolean = false;
  editModes: boolean[] = [];
  editMode: boolean = false;
  skillIds: number[] = [];
  selectedSkillNames: string[] = [];
  isTechnicalCategoryDisabled: boolean = false;
  isLoading: boolean = false;
  expnegative: boolean = false;
  isEditModeActive: boolean = false;


  newRow: any = {
    employeeMail: this.empMail,
    skillCategory: '',
    technicalCategory: '',
    skillId: '',
    skillTypeId: '',
    competencyLevelId: '',
    certification: '',
    description: '',
    comments: '',
    yearsOfExp: '',
    isDeleted: '0'
  };

  addNewRow() {
    this.newRows.push({
      employeeMail: this.empMail,
      skillCategory: '',
      technicalCategory: '',
      skillId: '',
      skillTypeId: '',
      competencyLevelId: '',
      certification: '',
      description: '',
      comments: '',
      yearsOfExp: '',
      isDeleted: 0
    });
  }

  /**
  * Check if the edit mode is enabled for the item at the specified index return this.editModes[index];
  */
  isEditMode(index: number): boolean {
    return this.editModes[index];
  }
  /**
   * Retrieve skill type and competency information
   * Toggle edit mode for the item and global edit mode
   * Keep track of the edited item
   */
  onEditMode(index: number) {
    this.getSkillType(this.skillTypeInput)
    this.getSkillCompetency(this.skillCompetencyInput)
    this.editModes[index] = !this.editModes[index];
    this.editMode = !this.editMode;
    this.editedRow = this.employeesSkillGet[index]
    this.isEditModeActive = !this.isEditModeActive;

  }

  public user:any=null;
  /**
   *  Retrieves the email of the logged-in user from localStorage
   *  Fetches all skills associated with the logged-in employee
   *  Fetches skill categories from the backend API
   *  Fetches skill types and competencies
   */
ngOnInit() {

  this.isLoading = true;
  
   var temp = localStorage.getItem("user");
   if(temp!=null && temp!=undefined){
    this.user = JSON.parse(temp);
   }
  this.empMail = this.user.email;
  console.log(this.user.email)
  // if (email) {
  //   this.empMail = JSON.parse(email);
  // }
  console.log(this.empMail)
  // setTimeout(()=>{
  this.getAllEmployeeSkills(this.empMail);
  //  },1000)
  this.empskillService.getSkillCategories(this.skillCatogoryInput).subscribe(
    (resp: string[]) => {
      this.skillCategories = resp as any[];
    },
    (error) => {
      Swal.fire({
        title: ' Failed to Fetch!',
        text: 'failed to fetch skill categories',
        icon: 'error',
        confirmButtonText: 'Ok',
      });
    }
  );
  this.getSkillType(this.skillTypeInput);
  this.getSkillCompetency(this.skillCompetencyInput)

}

  /**
   * Fetches all skills associated with the provided employee email
   */
getAllEmployeeSkills(empMail: string) {

  this.empskillService.getAllEmployeeSkills(empMail).subscribe(
    (resp: EmployeeSkillGet[]) => {
      this.employeesSkillGet = resp;
      console.log(this.employeesSkillGet)
      this.isLoading = false;
    },
    (error) => {
      Swal.fire({
        title: ' Failed to Fetch!',
        text: 'failed to fetch all employee skills',
        icon: 'error',
        confirmButtonText: 'Ok',
      });
    }
  );
}
  
  /**
  * Update the selected skill category
  * Fetch technical categories based on the selected skill category
  */
  onSelectSkillCategory(event: any, index: number) {
    this.skillCategory = event.target.value
    console.log(this.skillCategory);
    
    this.empskillService.getTechnicalCategory(this.skillCategory).subscribe((resp: any) => {
      this.technicalcategories[index] = resp;
    },
      (error) => {
        Swal.fire({
          title: ' Failed to Fetch!',
          text: 'failed to fetch  technical categories',
          icon: 'error',
          confirmButtonText: 'Ok',
        });
      })
  }

  /**
   * Retrieve the selected technical category
   * Fetch skills based on the selected technical category 
   */
  onSelectTechnicalCategory(event: any, index: number) {

    const selectedTechnicalCategory = event.target.value;
    console.log(selectedTechnicalCategory);
    
    this.empskillService
      .getSkillsForEmployee(selectedTechnicalCategory)
      .subscribe((res: any[]) => {
        this.skills[index] = res.filter(skill => {
          return !this.employeesSkillGet.some(employeeSkill => employeeSkill.skillId === skill.skillId) && !this.skillIds.includes(skill.skillId);
        });

      },
        (error) => {
          Swal.fire({
            title: ' Failed to Fetch!',
            text: 'failed to fetch skills',
            icon: 'error',
            confirmButtonText: 'Ok',
          });
        });
  }

  /**
   * Determine if the technical category is disabled based on the availability of skills
   */
  isTechnicalCategoryDisabledm(i: number) {
    this.isTechnicalCategoryDisabled = this.skills[i].length === 0;
  }

  /**
   * Retrieve the selected skill ID
   *  Find the corresponding skill object in the skills array
   * Add the selected skill name to the list of selected skill names
   */
  onSelectSkillName(event: any, i: number) {
    const selectedSkillId = event.target.value;
    let skill = (this.skills[i].find((skill: any) => skill.skillId === Number(selectedSkillId)));
    this.skillIds.push(skill.skillId)
  }

  /**
   * Fetch skill types based on the provided skill type
   */
  getSkillType(skillType: string) {
    this.empskillService
      .getSkilltype(skillType)
      .subscribe((res: any[]) => {
        this.skillTypes = res;
        this.skillTypes1 = res;
      },
        (error) => {
          // Swal.fire({
          //   title: ' Failed to Fetch!',
          //   text: 'failed to fetch  skillstype',
          //   icon: 'error',
          //   confirmButtonText: 'Ok',
          // });
        });

  }

  /**
   * Fetch skill competencies based on the provided skill category
   */
  getSkillCompetency(skillCategory: string) {
    this.empskillService
      .getSkillCompetency(skillCategory)
      .subscribe((res: any[]) => {
        this.skillCompetencies = res;
        this.skillCompetencies1 = res;
      },
        (error) => {
          // Swal.fire({
          //   title: ' Failed to Fetch!',
          //   text: 'failed to fetch skills competency',
          //   icon: 'error',
          //   confirmButtonText: 'Ok',
          // });
        });
  }

  /**
   * Fetch skill types based on the provided skill type
   * Fetch skill competencies based on the provided skill competency
   */
fetchValuesEdit(skillType: string, SkillCompetency: string, SkillCertification: string) {
  this.empskillService.getSkilltype(skillType).subscribe((res) => {
    this.skillTypes = res;

  }
    // ,
    //   (error) => {
    //     Swal.fire({
    //       title: ' Failed to Fetch!',
    //       text: 'failed to fetch skills type',
    //       icon: 'error',
    //       confirmButtonText: 'Ok',
    //     });
    //   }
  )

  this.empskillService.getSkillCompetency(SkillCompetency).subscribe((res) => {
    this.skillCompetencies = res;
  }
    ,
    (error) => {
      // Swal.fire({
      //   title: ' Failed to Fetch!',
      //   text: 'failed to fetch skillCompetencies',
      //   icon: 'error',
      //   confirmButtonText: 'Ok',
      // });
    }
  )
}
  /**
   * Update the skill type of the edited row based on the selected value
   */
  editedSkillType(event: any, index: number) {
    this.editedRow.skillTypeId = event.target.value
  }

  /**
   * Update the competency level of the edited row based on the selected value
   */
  editedCompetency(event: any, index: number) {
    this.editedRow.competencyLevelId = event.target.value
  }

  /**
   * Update the certification of the edited row based on the selected value
   */
  editedCertifcation(event: any, index: number) {
    this.editedRow.certification = event.target.value
  }

  /**
   * Update the description of the edited row based on the entered value
   */
  editedDescription(event: any, index: number) {
    this.editedRow.description = event.target.value
  }

  /**
   * Update the comments of the edited row based on the entered value
   */
  editedComments(event: any, index: number) {
    this.editedRow.comments = event.target.value
  }

  /**
   * Update the years of experience of the edited row based on the entered value
   */
  editedYearOfExp(event: any, index: number) {
    this.editedRow.yearsOfExp = event.target.value
  }

  /**
   * If in edit mode, save row changes
   *  Otherwise, toggle edit mode and initialize editedRow with employeeSkill data
   */
  onEditModeToggle() {
    if (this.isEditModee) {
      this.saveRowChanges();
    } else {
      this.isEditModee = !this.isEditModee;
      this.editModes.fill(this.isEditModee);
      this.editedRow = { ...this.employeeSkill };
    }
  }

  /**
  *  Send editedRow data to the backend to save changes
  *  If successful, exit edit mode
  */
saveRowChanges() {
  this.empskillService.editEmployeeSkill(this.editedRow, this.editedRow.employeeSkillId).subscribe(
    (resp) => {
      if (this.editMode == false) {
        Swal.fire({
          title: ' Suceessfully Edited',
          text: 'employee skills edited sucessfully',
          icon: 'success',
          confirmButtonText: 'Ok',
        });
        this.editMode = false;

      }
       this.getAllEmployeeSkills(this.empMail)

    },
    (error) => {
      Swal.fire({
        title: ' Edit failed!',
        text: 'failed to edit skill details',
        icon: 'error',
        confirmButtonText: 'Ok',
      });
    }
  );
}

  /**
   * Delete the row corresponding to the provided index from the backend
   *  Remove the row from the local array
   *  Remove the edit mode status for the deleted row
   */
  onRemove(index: number) {
    this.empskillService.deleteRow(this.employeesSkillGet[index].employeeSkillId).subscribe((res) => {
      const response = res;
    })
    this.employeesSkillGet.splice(index, 1);
    this.editModes.splice(index, 1);
  }

  /**
   * Open a confirmation dialog to confirm deletion
   *  If confirmed, remove the new row from the local array
   */
  openConfirmationDialogAdded(index: number) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: { title: 'Confirm Deletion', message: 'Are you sure you want to delete the record?' }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.newRows.splice(index, 1);
      }
    });
  }

  /**
   * Opens a confirmation dialog to confirm deletion
   *  If confirmed, calls the onRemove method
   */
openConfirmationDialog(index: number) {
  const dialogRef = this.dialog.open(ConfirmDialogComponent, {
    data: { title: 'Confirm Deletion', message: 'Are you sure you want to delete the record?' }
  });

  dialogRef.afterClosed().subscribe((result) => {
    if (result) {
      Swal.fire({
        title: 'Deleted Sucessfully',
        text: 'skill deleted',
        icon: 'success',
        confirmButtonText: 'Ok',
      });
      this.onRemove(index);
    }
  });
}

  /**
   *  Save extra added rows of employee skills to the backend
   * Display success or error message based on the response
   * Refresh the list of employee skills
   */
saveExtraAddedRows(employeeAddedSkill: EmployeeUiBean[]) {
  this.empskillService.postEmployeeSkills(employeeAddedSkill).subscribe(
    (res: EmployeeUiBean[]) => {
      Swal.fire({
        title: 'Saved Sucessfully',
        text: 'skill added',
        icon: 'success',
        confirmButtonText: 'Ok',
      });
      this.getAllEmployeeSkills(this.empMail)
    },
    (error) => {
      Swal.fire({
        title: 'Failed to Save!',
        text: 'failed to save skill details',
        icon: 'error',
        confirmButtonText: 'Ok',
      });
    }
  );

  this.newRows = [];
}


  /**
   * Refresh the current page by reloading it
   */
  refreshPage() {
    window.location.reload();
  }
   
  checkExp(event:any) {
    if (event.target.value< 0) {
      this.expnegative = true
    }
    else{
      this.expnegative = false

    }
  }

}

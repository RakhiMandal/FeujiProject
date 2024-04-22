import { Component, OnInit, numberAttribute } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import { switchMap } from 'rxjs/operators';
import { of } from 'rxjs';
import { EmployeesSkillsListDto } from '../../../../models/EmployeesSkillsListDto.model';
import { SkillsBean } from '../../../../models/SkillsBean.model';
import { SkillNamesDto } from '../../../../models/SkillNamesDto.model';
import Swal from 'sweetalert2';
import { SkillCompetency } from '../../../../models/SkillCompetency.model';


@Component({
  selector: 'app-skillgap-display',
  templateUrl: './skillgap-display.component.html',
  styleUrl: './skillgap-display.component.css'
})
export class SkillgapDisplayComponent implements OnInit {

  empSkills: EmployeesSkillsListDto[] = [];
  skillNames: string[] = [];
  skillCatogoryInput: string = 'Skill Category';
  skillCategories: any[] = [];
  selectedSkillCate: string = '';
  selectedTechnicalCate: string = '';
  selectedDesgn: string = '';
  selectedRoleName: string = '';
  isBorderBlue: boolean = false;
  isBorderBlue2: boolean = false;
  isBorderBlue3: boolean = false;
  skillompetencyBean: SkillCompetency[] = [];
  skillType: SkillNamesDto[] = [];
  empValues: EmployeesSkillsListDto[] = [];
  technicalCategories: any[] = [];
  empName: string = "";
  public page: number = 0;
  public size: number = 5;
  public totalPages: number = 0;
  public totalRecords: number = 0;
  public first: boolean = true;
  public last: boolean = false;

  uniqueRoles: { [key: string]: number[] } = {};
  uniqueRoleNames: string[] = [];
  skillIds: number[] = [];
  empMail: string = '';

  constructor(private http: HttpClient, public empskillService: EmployeeSkillService) { }

  /**
   *  Fetches skill categories from the backend
   *  Displays an error message if fetching fails
   */
  public user: any = null;
  ngOnInit(): void {
    var temp = localStorage.getItem("user");
    if (temp != null && temp != undefined) {
      this.user = JSON.parse(temp);
    }
    this.empMail = this.user.email;
    this.empskillService.getSkillCategories(this.skillCatogoryInput).subscribe(
      (resp) => {
        this.skillCategories = resp;
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
  }

  /**
   * Change the border style to blue
   */
  changeBorderStyle() {
    this.isBorderBlue = true;
  }
  /**
   * Change the border style to blue
   */
  changeBorderStyle2() {
    this.isBorderBlue2 = true;
  }
  /**
   * Change the border style to blue
   */
  changeBorderStyle3() {
    this.isBorderBlue3 = true;
  }

  /**
   * Update the selected skill category
   * Fetch technical categories based on the selected skill category
   * Display an error message if fetching fails
   */
  onSelectskillCat(skillcat: any) {

    this.selectedSkillCate = skillcat.target.value;
    this.empskillService.getTechnicalCategory(this.selectedSkillCate).subscribe(
      (resp) => {
        this.technicalCategories = resp;
      },
      (error) => {
        Swal.fire({
          title: ' Failed to Fetch!',
          text: 'failed to fetch technical categories',
          icon: 'error',
          confirmButtonText: 'Ok',
        });
      }
    );

  }

  /**
   * Extract values from the selected technical category
   *  Extract selected value and initialize variables
   *  Fetch roles based on the selected value
   *  Organize roles into unique groups and update uniqueRoleNames
   *  Display an error message if fetching fails
 */
  onSelectTechCat(techCat: any) {

    const values = techCat.target.value.split(',');
    this.selectedTechnicalCate = values[1];

    const val = values[0];
    this.uniqueRoles = {} as { [key: string]: any[] };
    this.empskillService.getRoles(val)
      .subscribe((res2) => {
        this.skillompetencyBean = res2;
        res2.forEach((skillComp: any) => {
          const { roleName, skillId } = skillComp;

          if (this.uniqueRoles.hasOwnProperty(roleName)) {
            this.uniqueRoles[roleName].push(skillId);
          } else {
            this.uniqueRoles[roleName] = [skillId];
          }
        });

        this.uniqueRoleNames = Object.keys(this.uniqueRoles);
      }, (error) => {
        Swal.fire({
          title: ' Failed to Fetch!',
          text: 'failed to fetch employee roles',
          icon: 'error',
          confirmButtonText: 'Ok',
        });
      }

      );
  }

  /**
   * Update the selected role name
   *  Check if the selected role name exists in uniqueRoles
   *  If it exists, update skillIds with the corresponding skill IDs
   *  If not, display an error message
   */
  onSelectRole(role: any) {

    this.page = 0;
    this.selectedRoleName = role.target.value;
    if (this.uniqueRoles.hasOwnProperty(this.selectedRoleName)) {
      this.skillIds = this.uniqueRoles[this.selectedRoleName];
    } else {
      (error: string) => {
        Swal.fire({
          title: ' Failed to Fetch!',
          text: 'failed to fetch Employee Role',
          icon: 'error',
          confirmButtonText: 'Ok',
        });
      }
    }
  }

  /**
   *  Increment the page number
   * Trigger the search function
   */
  onNext() {
    this.page = this.page + 1;
    this.onsearch();
  }

  /**
   * Decrement the page number
   * Trigger the search function
   */
  onPrevious() {
    this.page = this.page - 1;
    this.onsearch();
  }

  /**
   * Set the page number to the last page
   * Trigger the search function
   */
  onLast() {
    this.page = this.totalPages - 1;
    this.onsearch();
  }

  /**
   * Set the page number to the first page
   * Trigger the search function
   */
  onFirst() {
    this.page = 0;
    this.onsearch();
  }

  /**
   * Update the page size
   *  Reset the page number to the first page
   *  Trigger the search function
   */
  onSelectPageSize(size: any) {
    this.size = size.target.value;
    this.page = 0;
    this.onsearch();
  }

  /**
   * Fetch employee skills by skill ID, page number, and page size
    * Update pagination information and employee skills data
    * Fetch skill names associated with the skill IDs
    * Display error message if fetching skill names fai
   */
  onsearch() {
    this.skillType = [];
    const rolename = this.selectedRoleName;
    this.empskillService.getEmployeSkillsBySkillId(this.skillIds, this.page, this.size, rolename)
      .pipe(
        switchMap((res) => {
          this.first = res.first;
          this.last = res.last;
          this.totalRecords = res.totalRecords;
          this.empSkills = res.skillList;
          this.totalPages = res.totalPages;
          this.empSkills.map(e => e);
          return of(null);
        }
        )
      )
      .subscribe();

    this.empskillService.getSkillNames(this.skillIds).
      subscribe((resp) => {
        // this.skillType=resp;
        const uniqueSkillNames: string[] = [];

        // Iterate over the response and add unique skill names
        resp.forEach(skillNameDto => {
          const skillName = skillNameDto.skillName;
          if (!uniqueSkillNames.includes(skillName)) {
            uniqueSkillNames.push(skillName);
            this.skillType.push(skillNameDto);
          }
        });

        // Assign the unique skill names array
        this.skillNames = uniqueSkillNames;
      },
        (error) => {
          Swal.fire({
            title: ' Failed to Fetch!',
            text: 'failed to fetch Skill names',
            icon: 'error',
            confirmButtonText: 'Ok',
          });
        })
  }

  generateResult(exReferenceDetailsValues: string, acReferenceDetailsValues: string): string {
    const levelMapping: { [key: string]: number } = {
      "No Skill": 0,
      "Beginner": 1,
      "Intermediate": 2,
      "Expert": 3
    };

    const exLevel: number = levelMapping.hasOwnProperty(exReferenceDetailsValues) ? levelMapping[exReferenceDetailsValues] : -1;
    const acLevel: number = levelMapping.hasOwnProperty(acReferenceDetailsValues) ? levelMapping[acReferenceDetailsValues] : -1;

    if (exLevel === -1) {
      console.warn("Unexpected value for exReferenceDetailsValues:", exReferenceDetailsValues);
    }
    if (acLevel === -1) {
      console.warn("Unexpected value for acReferenceDetailsValues:", acReferenceDetailsValues);
    }
    const difference = exLevel - acLevel;

    if (exLevel === 0) {
      return '../assets/img/noskill.png';
    } else if (difference === 0) {
      return '../assets/img/noGap.png';
    } else if (difference === -1) {
      return '../assets/img/expert.png';
    }
    else if (difference === 1) {
      return '../assets/img/lowgap.png';
    } else {
      return '../assets/img/lessSkill.png';
    }
  }

  generateResultForImg(exReferenceDetailsValues: string, acReferenceDetailsValues: string): any {
    const levelMapping: { [key: string]: number } = {
      "No Skill": 0,
      "Beginner": 1,
      "Intermediate": 2,
      "Expert": 3
    };

    const exLevel: number = levelMapping.hasOwnProperty(exReferenceDetailsValues) ? levelMapping[exReferenceDetailsValues] : -1;
    const acLevel: number = levelMapping.hasOwnProperty(acReferenceDetailsValues) ? levelMapping[acReferenceDetailsValues] : -1;

    if (exLevel === -1) {
      console.warn("Unexpected value for exReferenceDetailsValues:", exReferenceDetailsValues);
    }
    if (acLevel === -1) {
      console.warn("Unexpected value for acReferenceDetailsValues:", acReferenceDetailsValues);
    }
    return (exLevel - acLevel);
  }

  /**
   *  Determine the competency text based on the competency level ID
   *  Return a string containing expected competency, actual competency, and gap  
   */
  getCompetencyText(skillBean: SkillsBean): string {
    let value: number = this.generateResultForImg(skillBean.expectedCompetency, skillBean.actualCompetency)
    switch (value) {
      case 0:
        return 'Expected: ' + skillBean.expectedCompetency +
          '\nActual: ' + skillBean.actualCompetency +
          '\nGap: ' + 'No Gap';
      case -1:
        return 'Expected: ' + skillBean.expectedCompetency +
          '\nActual: ' + skillBean.actualCompetency +
          '\nGap: ' + 'Expert';
      case 1:
        return 'Expected: ' + skillBean.expectedCompetency +
          '\nActual: ' + skillBean.actualCompetency +
          '\nGap: ' + 'Low Gap';
      default:
        return 'Expected: ' + skillBean.expectedCompetency +
          '\nActual: ' + skillBean.actualCompetency +
          '\nGap: High Gap';
    }
  }
}

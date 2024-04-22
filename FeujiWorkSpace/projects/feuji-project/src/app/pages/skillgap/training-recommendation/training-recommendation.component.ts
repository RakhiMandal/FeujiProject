import { Component, OnInit } from '@angular/core';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import { HttpClient } from '@angular/common/http';
import Swal from 'sweetalert2';
import { EmployeeSkillObject } from '../../../../models/EmployeeSkillObject.model';

@Component({
  selector: 'app-training-recommendation',
  templateUrl: './training-recommendation.component.html',
  styleUrl: './training-recommendation.component.css'
})
export class TrainingRecommendationComponent implements OnInit {
  public user: any=null;
  

  constructor(private http: HttpClient, private empskillService: EmployeeSkillService) { }

  public empMail: string = '';
  public empSkills: EmployeeSkillObject[] = [];
  public expectedLevel:number[]=[];
  public actualLevel:number[]=[];
  public highGapSkills:EmployeeSkillObject[]=[];
  public moderateGapSkills:EmployeeSkillObject[]=[];
  public highSkillCategory:string[]=[];
  public highSkillTechCategory:string[]=[];
  public skillGapMessage:number=0;

  ngOnInit(): void {
    var temp = localStorage.getItem("user");

    if(temp!=null && temp!=undefined){
     this.user = JSON.parse(temp);
    }
    this.empMail = this.user.email;
     this.getEmployeeSkillsByEmail(this.empMail);

  }

  getEmployeeSkillsByEmail(empMail:string){
    this.empskillService.getSkillsByEmployeeId(empMail).subscribe(
      (resp) => {
        this.empSkills = resp ;
        this.empSkills.map(skill => {
         const result=this.generateResult(skill.exReferenceDetailsValues,skill.acReferenceDetailsValues)
         if(result>1){
          this.highGapSkills.push(skill);  
         }
         if(result===1){
          this.moderateGapSkills.push(skill);
         }
       
        });
        if(this.highGapSkills.length==0){
          this.skillGapMessage=2;      
         }
         if(this.moderateGapSkills.length==0){
          this.skillGapMessage=1;
         }
      },
      (error) => {
        alert("failled")
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
   * Map skill levels to numeric values using a predefined mapping
   */
  mapLevelToNumeric(level: string): number {
    const levelMapping: { [key: string]: number } = {
      "No Skill": 0,
      "Beginner": 1,
      "Intermediate": 2,
      "Expert": 3
    };
    return levelMapping.hasOwnProperty(level) ? levelMapping[level] : -1;
  }

 
  /**
   * Generates a result image path based on expected and actual skill levels
   */
  generateResult(exReferenceDetailsValues:string,acReferenceDetailsValues:string) {
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
    return difference;

    // if (exLevel === 0) {
    //   return 'no skill';
    // } else if (difference === 0) {
    //   return 'noGap';
    // } else if (difference === -1) {
    //   return 'Morethan Expected';
    // }
    // else if (difference === 1) {
    //   return 'lowGap';
    // } else {
    //   return 'highGap';
    // }


  }


}

import { HttpClient } from '@angular/common/http';
import { Component, ViewChild, ElementRef } from '@angular/core';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import 'jspdf-autotable';
import jsPDF from 'jspdf';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-employee-skill-gap',
  templateUrl: './employee-skill-gap.component.html',
  styleUrl: './employee-skill-gap.component.css'
})
export class EmployeeSkillGapComponent {

  constructor(private http: HttpClient, private empskillService: EmployeeSkillService) { }

  public barChartLabels: string[] = [];
  public barChartType: string = 'bar';
  public barChartLegend: boolean = true;
  isBorderBlue: boolean = false;
  skillCatogoryInput: string = 'Skill Category'
  employeeName: string = '';
  designation: string = '';
  empMail: string = '';
  public email: string = '';
  public getMySkills: any[] = [];
  referenceDetailId: number = 0;
  technicalCategories: any[] = [];
  skillCategories: any[] = [];
  @ViewChild('graphCanvas') graphCanvas!: ElementRef;

  /**
   * Change the border style to blue
   */
  changeBorderStyle() {
    this.isBorderBlue = true;
  }

  public barChartData: any[] = [
    { data: [], label: 'Expected Level', backgroundColor: 'orange', borderColor: 'rgba(255, 99, 132, 1)' },
    { data: [], label: 'Actual Level', backgroundColor: 'black', borderColor: 'rgba(54, 162, 235, 1)' }
  ];


  /**
   *  Retrieves the email of the logged-in user from localStorage
   *  Fetches skills associated with the employee
   *  Fetches skill categories from the backend
   */
  public user:any=null;
  ngOnInit(): void {
   
    var temp = localStorage.getItem("user");
    if(temp!=null && temp!=undefined){
     this.user = JSON.parse(temp);
    }
   this.empMail = this.user.email;
    this.getSkillsOfEmployee()
    this.empskillService.getSkillCategories(this.skillCatogoryInput).subscribe(

      (resp) => {
        this.skillCategories = resp;
      }
    );
  }

  /**
   *Update the referenceDetailId based on the selected value
    * Fetch employee skills based on the employee email and referenceDetailId
    *  Update chart labels and data based on the fetched skills
   */
   onSelectskillCat(referenceDetailId: any) {
    if (referenceDetailId.target.value == 'allCategories') {
      this.getSkillsOfEmployee();
    }
    else {
      this.referenceDetailId = referenceDetailId.target.value;
      this.empskillService.getSkillsOfEmployee(this.empMail, this.referenceDetailId).subscribe(
        (response) => {
          this.getMySkills = response;
        },
        (error) => {
          Swal.fire({
            title: ' Failed to Fetch!',
            text: 'failed to fetch employee skills',
            icon: 'error',
            confirmButtonText: 'Ok',
          });
        }
      );
    }
  }

  /**
   * Fetch skills associated with the employee by their email
   */
  getSkillsOfEmployee(): void {
   
    this.empskillService.getSkillsByEmployeeId(this.empMail).subscribe(
      (response) => {
        this.getMySkills = response ;
      
      }
    );
  }

  /**
   * Generates a result image path based on expected and actual skill levels
   */
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
   *  Prepare data for download
   *  Create a PDF document
   *  Add title to the PDF document
   *  Add employee details to the PDF document
   *  Add images and their descriptions to the PDF document
   *  Add employee skills table to the PDF document
   *  Save the PDF document
   */
  downloadData() {
    const dataToDownload = {
      skills: this.getMySkills,
      barChartData: this.barChartData,
      barChartLabels: this.barChartLabels
    };

    const jsonData = JSON.stringify(dataToDownload);

    const pdf: any = new jsPDF();
    const pdfWidth = pdf.internal.pageSize.getWidth();
    const textWidth = pdf.getStringUnitWidth('EMPLOYEE SKILL REPORT') * pdf.internal.getFontSize() / pdf.internal.scaleFactor;

    const xPosition = (pdfWidth - textWidth) / 2;

    pdf.setFontSize(16);
    pdf.setFont("helvetica", "bold");
    pdf.text('EMPLOYEE SKILL REPORT', xPosition, 10);

    var imgMoreThanExpected = '../assets/img/expert.png';
    var imgNoGap = '../assets/img/noGap.png';
    var imgLowGap = '../assets/img/lowgap.png';
    var imgSkillNotRequired = '../assets/img/noskill.png';
    var imgHighGap = '../assets/img/lessSkill.png';
    const startY = 10;
    pdf.setFontSize(10);
    pdf.setFont("helvetica", "bold");
    pdf.text('EMPLOYEE DETAILS:', 10, startY + 15);
    pdf.setFontSize(10);
    pdf.setFont("helvetica", "normal");
    pdf.text('Employee Name: ' + this.getMySkills[0]?.employeeName, 10, startY + 20);
    pdf.text('Email: ' + this.empMail, 10, startY + 25);
    pdf.text('Designation: ' + this.getMySkills[0]?.designation, 10, startY + 30);

    pdf.text('More than expected', 160, startY + 10);
    pdf.addImage(imgMoreThanExpected, 'PNG', 155, startY + 7, 4, 4);

    pdf.text('No Gap', 160, startY + 15);
    pdf.addImage(imgNoGap, 'PNG', 155, startY + 12, 4, 4);

    pdf.text('Low Gap', 160, startY + 20);
    pdf.addImage(imgLowGap, 'PNG', 155, startY + 17, 4, 4);

    pdf.text('High Gap', 160, startY + 25);
    pdf.addImage(imgHighGap, 'PNG', 155, startY + 22, 4, 4);

    pdf.text('Skill not required', 160, startY + 30);
    pdf.addImage(imgSkillNotRequired, 'PNG', 155, startY + 27, 4, 4);

    pdf.autoTable({
      head: [['Skill Name', 'Expected Level', 'Actual Level', 'Description', 'Results']],
      body: this.getMySkills.map(skill => [skill.skillName, skill.exReferenceDetailsValues, skill.acReferenceDetailsValues, skill.description, '']),
      startY: startY + 40,
      didDrawCell: (data: { row: { index: number }; column: { index: number }; section: string; cell: { x: number; y: number, width: number, height: number } }) => {
        if (data.row.index >= 0 && data.column.index === 4 && data.section === 'body') {
          const rowIndex = data.row.index;
          if (rowIndex >= 0 && rowIndex < this.getMySkills.length) {
            const skill = this.getMySkills[rowIndex];
            const imageData = this.generateResult(skill.exReferenceDetailsValues, skill.acReferenceDetailsValues);

            const x = data.cell.x + 1;
            const y = data.cell.y + 1;

            pdf.addImage(imageData, 'PNG', x, y, 8, 6);
          }
        }
      }
    });
    pdf.save('employee_skills_data.pdf');
  }
}

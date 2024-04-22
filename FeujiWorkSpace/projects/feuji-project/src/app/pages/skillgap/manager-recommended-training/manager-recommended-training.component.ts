import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import { SubSkillData } from '../../../../models/SubSkillData.service';
import { TrainigRecommendedEmployeesDto } from '../../../../models/TrainigRecommendedEmployeesDto.model';
import Swal from 'sweetalert2';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import ExcelJS from 'exceljs';



@Component({
  selector: 'app-manager-recommended-training',
  templateUrl: './manager-recommended-training.component.html',
  styleUrl: './manager-recommended-training.component.css'
})
export class ManagerRecommendedTrainingComponent {
  [x: string]: any;

  accordionData: any[] = [];
  accordionSubData: any[] = [];
  panelOpenState: boolean = false;
  selectedTechCat: any;
  allSkills: any[] = [];
  allSkillIds: number[] = [];
  employees: TrainigRecommendedEmployeesDto[] = [];
  updatedEmployees: TrainigRecommendedEmployeesDto[] = [];
  size: number = 0;
  selectedSubItem: any;
  displayedColumns: string[] = ['employeeName', 'designation', 'email', 'skillName', 'actualCompetency', 'expectedCompetency', 'result']; // Add more column names as needed



  constructor(private employeeSkillService: EmployeeSkillService,
    public dialog: MatDialog, private subSkillDataSevice: SubSkillData) {

  }
  ngOnInit(): void {
    this.loadSkillCategories();
  }

  loadSkillCategories() {
    this.employeeSkillService.getSkillCategoryNames().subscribe((data: any[]) => {
      this.accordionData = data;
    });
  }

  onSelectSkillCategory(selectedSkillCategory: any) {
    this['selectedSkillCategory'] = selectedSkillCategory;
    this.employeeSkillService.getTechnicalCategory(selectedSkillCategory).subscribe((subSkills: any[]) => {
      this.accordionSubData = subSkills;
      this.subSkillDataSevice.updateAccordionSubData(subSkills);
    });
  }
  onSelectTechCat(techCat: any) {
    this.employees = [];
    this.updatedEmployees = [];
    this.size = 0;
    this.allSkills = [];
    this.allSkillIds = [];
    this.selectedSubItem = this.accordionSubData.find(item => item.referenceDetailId === techCat);
    this.calculateSize();
    this['selectedSubTechCat'] = this.selectedSubItem ? this.selectedSubItem.referenceDetailValue : 'N/A';

    this.employeeSkillService.getSkills(techCat).subscribe((skills: any[]) => {
      this.allSkills = skills;
      this.getAllSkillIds(this.allSkills);
      this.getEmployees(this.allSkillIds);
    },
      (error) => {
        console.error(error);
      }
    )
  }


  getAllSkillIds(allSkills: any) {
    for (let index = 0; index < this.allSkills.length; index++) {
      this.allSkillIds[index] = this.allSkills[index].skillId;
    }
  }

  getEmployees(allSkillIds: number[]) {
    this.employeeSkillService.getTrainingRecommendedEmployees(allSkillIds)
      .subscribe((resp) => {
        this.employees = resp;

        this.filter(this.employees);
      },
        (error) => {
          Swal.fire({
            title: 'No Employees found !',
            icon: 'error',
            confirmButtonText: 'Ok',
          });



        });

  }


  filter(employees: TrainigRecommendedEmployeesDto[]) {
    this.updatedEmployees = [];
    for (let index = 0; index < employees.length; index++) {
      if (this.generateResult(employees[index].expectedCompetency, employees[index].actualCompetency) > 1) {
        const updatedEmployee = new TrainigRecommendedEmployeesDto(
          employees[index].employeeId,
          employees[index].employeeCode,
          employees[index].designition,
          employees[index].email,
          employees[index].skillName,
          employees[index].actualCompetency,
          employees[index].expectedCompetency,
          employees[index].actualcompetencyLevelId,
          employees[index].expectedcompetencyLevelId,
          employees[index].competencyLevelId,
          employees[index].employeeName
        );
        this.updatedEmployees.push(updatedEmployee);
      }
    }
    this.size = this.updatedEmployees.length
  }

  generateResult(exReferenceDetailsValues: string, acReferenceDetailsValues: string): number {
    const levelMapping: { [key: string]: number } = {
      "No Skill": 0,
      "Beginner": 1,
      "Intermediate": 2,
      "Expert": 3
    };

    const exLevel: number = levelMapping.hasOwnProperty(exReferenceDetailsValues) ? levelMapping[exReferenceDetailsValues] : -1;
    const acLevel: number = levelMapping.hasOwnProperty(acReferenceDetailsValues) ? levelMapping[acReferenceDetailsValues] : -1;
    return exLevel - acLevel;
  }

  calculateSize() {
    this.size = this.updatedEmployees.length;
  }
  generateResult2(exReferenceDetailsValues: string, acReferenceDetailsValues: string): string {
    const levelMapping: { [key: string]: number } = {
      "No Skill": 0,
      "Beginner": 1,
      "Intermediate": 2,
      "Expert": 3
    };

    const exLevel: number = levelMapping.hasOwnProperty(exReferenceDetailsValues) ? levelMapping[exReferenceDetailsValues] : -1;
    const acLevel: number = levelMapping.hasOwnProperty(acReferenceDetailsValues) ? levelMapping[acReferenceDetailsValues] : -1;


    const difference = exLevel - acLevel;

    if (difference === 2) {
      return '../assets/img/lessSkill.png';
    } else {
      return '../assets/img/moreGap.jpg';
    }
  }

  downloadExcel() {
    const selectedSkillCategory = this['selectedSkillCategory'] || 'N/A';
    const selectedSubTechCat = this['selectedSubTechCat'] || 'N/A';
    const workbook = new ExcelJS.Workbook();
    const worksheet = workbook.addWorksheet('Recommended Training');
    const categoryData = [
      ['Skill Category', 'SubSkill Category'],
      [selectedSkillCategory, selectedSubTechCat],
      [],
      ['EmployeeName', 'Designation', 'Email', 'SkillName', 'ActualCompetency', 'ExpectedCompetency']
    ];
    categoryData.forEach((row, index) => {
      const sheetRow = worksheet.addRow(row);
      if (index < 1) {
        sheetRow.eachCell(cell => {
          cell.font = { bold: true };
        });
      } else if (index === 3) {
        sheetRow.font = { bold: true };
      }
    });
    this.updatedEmployees.forEach(employee => {
      worksheet.addRow([
        employee.employeeName,
        employee.designition,
        employee.email,
        employee.skillName,
        employee.actualCompetency,
        employee.expectedCompetency
      ]);
    });
    workbook.xlsx.writeBuffer()
      .then(buffer => {
        const fileName = 'recommended_training.xlsx';
        const blob = new Blob([buffer], { type: 'application/octet-stream' });
        saveAs(blob, fileName);
      });
  }


}

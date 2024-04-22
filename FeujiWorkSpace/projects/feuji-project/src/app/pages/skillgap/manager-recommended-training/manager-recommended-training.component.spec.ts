import { TestBed, ComponentFixture } from '@angular/core/testing';
import { ManagerRecommendedTrainingComponent } from './manager-recommended-training.component';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import { MatDialog } from '@angular/material/dialog';
import { of } from 'rxjs';
import { TrainigRecommendedEmployeesDto } from '../../../../models/TrainigRecommendedEmployeesDto.model';

describe('ManagerRecommendedTrainingComponent', () => {
  let component: ManagerRecommendedTrainingComponent;
  let fixture: ComponentFixture<ManagerRecommendedTrainingComponent>;
  let employeeSkillService: EmployeeSkillService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ManagerRecommendedTrainingComponent],
      providers: [EmployeeSkillService, MatDialog]
    }).compileComponents();

    fixture = TestBed.createComponent(ManagerRecommendedTrainingComponent);
    component = fixture.componentInstance;
    employeeSkillService = TestBed.inject(EmployeeSkillService);
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should load skill categories on initialization', () => {
    const mockSkillCategories = ['Category1', 'Category2'];
    spyOn(employeeSkillService, 'getSkillCategoryNames').and.returnValue(of(mockSkillCategories));

    component.ngOnInit();

    expect(component.accordionData).toEqual(mockSkillCategories);
  });

  it('should update accordionSubData on selecting a skill category', () => {
    const selectedSkillCategory = 'Category1';
    const mockSubSkills = ['SubSkill1', 'SubSkill2'];
    spyOn(employeeSkillService, 'getTechnicalCategory').and.returnValue(of(mockSubSkills));

    component.onSelectSkillCategory(selectedSkillCategory);

    expect(component['selectedSkillCategory']).toEqual(selectedSkillCategory);
    expect(component.accordionSubData).toEqual(mockSubSkills);
  });

  it('should fetch employees and skills on selecting a technical category', () => {
    const techCat = 1;
    const mockSkills = [{ skillId: 1, skillName: 'Skill1' }];
    const mockEmployees: TrainigRecommendedEmployeesDto[] = [
      {
        employeeId: 1,
        employeeCode: 'E001',
        designition: 'Manager',
        email: 'manager@example.com',
        skillName: 'Skill1',
        actualCompetency: 'Intermediate',
        expectedCompetency: 'Expert',
        actualcompetencyLevelId: 0,
        expectedcompetencyLevelId: 0,
        competencyLevelId: 0,
        employeeName: ''
      }
    ];

    spyOn(employeeSkillService, 'getSkills').and.returnValue(of(mockSkills));
    spyOn(employeeSkillService, 'getTrainingRecommendedEmployees').and.returnValue(of(mockEmployees));

    component.onSelectTechCat(techCat);

    expect(component.allSkills).toEqual(mockSkills);
    expect(component.updatedEmployees).toEqual(mockEmployees);
    expect(component.size).toEqual(mockEmployees.length);
  });

  it('should generate result difference correctly', () => {
    const exReferenceDetailsValues = 'Expert';
    const acReferenceDetailsValues = 'Intermediate';

    const result = component.generateResult(exReferenceDetailsValues, acReferenceDetailsValues);

    expect(result).toEqual(3 - 2); 
  });

  it('should generate and download an Excel file', () => {
    component.updatedEmployees = [
      {
        employeeName: 'John Doe',
        designition: 'Manager',
        email: 'john.doe@example.com',
        skillName: 'Angular',
        actualCompetency: 'Intermediate',
        expectedCompetency: 'Expert',
        employeeId: 0,
        employeeCode: '',
        actualcompetencyLevelId: 0,
        expectedcompetencyLevelId: 0,
        competencyLevelId: 0
      }
    ];

    spyOn(window, 'saveAs'); 

    component.downloadExcel();

    expect(window.saveAs).toHaveBeenCalledOnceWith(jasmine.any(Blob), 'recommended_training.xlsx');
  });
});

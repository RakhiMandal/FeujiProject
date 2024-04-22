import { TestBed, ComponentFixture, tick, fakeAsync } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { EmployeeSkillGapComponent } from './employee-skill-gap.component';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import { of } from 'rxjs';
import { ElementRef } from '@angular/core';

describe('EmployeeSkillGapComponent', () => {
  let component: EmployeeSkillGapComponent;
  let fixture: ComponentFixture<EmployeeSkillGapComponent>;
  let employeeSkillService: EmployeeSkillService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmployeeSkillGapComponent],
      imports: [HttpClientTestingModule],
      providers: [EmployeeSkillService]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeSkillGapComponent);
    component = fixture.componentInstance;
    employeeSkillService = TestBed.inject(EmployeeSkillService);

    spyOn(localStorage, 'getItem').and.returnValue('"test@example.com"');

    fixture.detectChanges();
  });

  it('should initialize component properties and fetch skill categories on ngOnInit', () => {
    const mockSkillCategories = ['Category1', 'Category2'];

    spyOn(employeeSkillService, 'getSkillCategories').and.returnValue(of(mockSkillCategories));

    component.ngOnInit();

    expect(component.empMail).toEqual('test@example.com');
    expect(employeeSkillService.getSkillCategories).toHaveBeenCalledWith('Skill Category');
    expect(component.skillCategories).toEqual(mockSkillCategories);
  });

  it('should fetch skills of the employee on calling getSkillsOfEmployee', () => {
    const mockSkills = [{ skillName: 'Skill1', exReferenceDetailsValues: 'Beginner', acReferenceDetailsValues: 'Intermediate' }];

    spyOn(employeeSkillService, 'getSkillsByEmployeeId').and.returnValue(of(mockSkills));

    component.getSkillsOfEmployee();

    expect(employeeSkillService.getSkillsByEmployeeId).toHaveBeenCalledWith('test@example.com');
    expect(component.getMySkills).toEqual(mockSkills);
  });

  it('should update referenceDetailId and fetch employee skills on calling onSelectskillCat with specific category', () => {
    const mockSkills = [{ skillName: 'Skill2', exReferenceDetailsValues: 'Beginner', acReferenceDetailsValues: 'Intermediate' }];
    const referenceDetailId = { target: { value: 1 } };

    spyOn(employeeSkillService, 'getSkillsOfEmployee').and.returnValue(of(mockSkills));

    component.onSelectskillCat(referenceDetailId);

    expect(component.referenceDetailId).toEqual(1);
    expect(employeeSkillService.getSkillsOfEmployee).toHaveBeenCalledWith('test@example.com', 1);
    expect(component.getMySkills).toEqual(mockSkills);
  });

  it('should update referenceDetailId and fetch all employee skills on calling onSelectskillCat with allCategories', () => {
    const mockSkills = [{ skillName: 'Skill3', exReferenceDetailsValues: 'Expert', acReferenceDetailsValues: 'Intermediate' }];
    const referenceDetailId = { target: { value: 'allCategories' } };
  
    spyOn(employeeSkillService, 'getSkillsByEmployeeId').and.returnValue(of(mockSkills));
  
    component.onSelectskillCat(referenceDetailId);
  
    expect(component.referenceDetailId).toEqual(-1);
  
    expect(employeeSkillService.getSkillsByEmployeeId).toHaveBeenCalledWith('test@example.com');
  
    expect(component.getMySkills).toEqual(mockSkills);
  });
  
  

  it('should change border style to blue on calling changeBorderStyle', () => {
    component.changeBorderStyle();
    expect(component.isBorderBlue).toBeTrue();
  });

  it('should generate result image path based on skill levels', () => {
    const imagePath = component.generateResult('Beginner', 'Intermediate');
    expect(imagePath).toEqual('../assets/img/lowgap.png');
  });

  it('should map skill level to numeric value', () => {
    const numericValue = component.mapLevelToNumeric('Intermediate');
    expect(numericValue).toEqual(2);
  });

  it('should download data and generate PDF', () => {
    const mockSkills = [{ skillName: 'Skill4', exReferenceDetailsValues: 'Expert', acReferenceDetailsValues: 'No Skill' }];
    component.getMySkills = mockSkills;

    const saveSpy = spyOn<any>(window.HTMLCanvasElement.prototype, 'toDataURL').and.callFake(() => 'data:image/png;base64,abcd');
    const downloadSpy = spyOn<any>(component, 'downloadPDF').and.stub();

    component.downloadData();

    expect(saveSpy).toHaveBeenCalled();
    expect(downloadSpy).toHaveBeenCalled();
  });
});

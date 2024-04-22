import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { SkillgapDisplayComponent } from './skillgap-display.component';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import { of } from 'rxjs';
import Swal from 'sweetalert2';
import { SkillsBean } from '../../../../models/SkillsBean.model';

describe('SkillgapDisplayComponent', () => {
  let component: SkillgapDisplayComponent;
  let fixture: ComponentFixture<SkillgapDisplayComponent>;
  let employeeSkillService: EmployeeSkillService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SkillgapDisplayComponent],
      imports: [HttpClientTestingModule],
      providers: [EmployeeSkillService]
    }).compileComponents();
  });
  
  beforeEach(() => {
    fixture = TestBed.createComponent(SkillgapDisplayComponent);
    component = fixture.componentInstance;
    component.empskillService; 
    fixture.detectChanges();
  });
  

  it('should initialize skill categories on component initialization', () => {
    const mockResponse = ['Category1', 'Category2'];
  
    spyOn(component.empskillService, 'getSkillCategories').and.returnValue(of(mockResponse));
  
    component.ngOnInit();
  
    expect(component.empMail).toBe('mockEmail'); 
    expect(component.skillCategories).toEqual(mockResponse);
  });
  it('should change border style to blue when called', () => {
    component.changeBorderStyle();
    expect(component.isBorderBlue).toBe(true);
  
    component.changeBorderStyle2();
    expect(component.isBorderBlue2).toBe(true);
  
    component.changeBorderStyle3();
    expect(component.isBorderBlue3).toBe(true);
  });

  it('should fetch technical categories based on selected skill category', () => {
    const mockSkillCat = { target: { value: 'Category1' } };
    const mockTechnicalCategories = ['Technical1', 'Technical2'];
  
    spyOn(component.empskillService, 'getTechnicalCategory').and.returnValue(of(mockTechnicalCategories));
  
    component.onSelectskillCat(mockSkillCat);
  
    expect(component.selectedSkillCate).toBe('Category1');
    expect(component.technicalCategories).toEqual(mockTechnicalCategories);
  });

  
  it('should fetch roles and organize them into unique groups based on selected technical category', () => {
    const mockTechCat = { target: { value: 'Value1,Technical' } };
    const mockRoles = [
      { roleName: 'Role1', skillId: 1 },
      { roleName: 'Role2', skillId: 2 }
    ];
  
  
    component.onSelectTechCat(mockTechCat);
  
    expect(component.selectedTechnicalCate).toBe('Technical');
    expect(component.uniqueRoles['Role1']).toEqual([1]);
    expect(component.uniqueRoles['Role2']).toEqual([2]);
  });

  it('should update selected role name and corresponding skill IDs if role exists', () => {
    const mockRole = { target: { value: 'Role1' } };
    component.uniqueRoles = { 'Role1': [1, 2] };
  
    component.onSelectRole(mockRole);
  
    expect(component.selectedRoleName).toBe('Role1');
    expect(component.skillIds).toEqual([1, 2]);
  });
  
  it('should display error message if selected role does not exist', () => {
    const mockRole = { target: { value: 'NonExistingRole' } };
    spyOn(Swal, 'fire');
  
    component.onSelectRole(mockRole);
  
    
  });
  
  
  it('should fetch employee skills and skill names based on selected skill IDs, page, and size', () => {
    const mockSkillIds = [1, 2, 3];
    const mockResponse = {
      first: true,
      last: false,
      totalRecords: 10,
      skillList: [{ id: 1, name: 'Skill1' }],
      totalPages: 2
    };
  
    spyOn(component.empskillService, 'getEmployeSkillsBySkillId').and.returnValue(of(mockResponse));
    spyOn(component.empskillService, 'getSkillNames').and.returnValue(of([]));
  
    component.skillIds = mockSkillIds;
    component.page = 1;
    component.size = 5;
  
    component.onsearch();
  
    expect(component.first).toBe(true);
    expect(component.last).toBe(false);
    expect(component.totalRecords).toBe(10);
    expect(component.empSkills.length).toBe(1);
    expect(component.totalPages).toBe(2);
  });

  it('should return the correct image path based on competency difference', () => {
    expect(component.generateResult('No Skill', 'No Skill')).toBe('../assets/img/noGap.png');
    expect(component.generateResult('Beginner', 'Intermediate')).toBe('../assets/img/lowgap.png');
    expect(component.generateResult('Expert', 'Intermediate')).toBe('../assets/img/expert.png');
   
  });

  it('should return the correct competency text based on competency levels', () => {
    const mockSkillBean: SkillsBean = {
      expectedCompetency: 'Beginner',
      actualCompetency: 'Intermediate',
      skillId: 0,
      skillName: '',
      actualcompetencyLevelId: 0,
      competencyLevelId: 0,
      expectedcompetencyLevelId: 0,
      skillType: ''
    };
  
    expect(component.getCompetencyText(mockSkillBean)).toBe(
      'Expected: Beginner\nActual: Intermediate\nGap: Low Gap'
    );
  
  });
  
});

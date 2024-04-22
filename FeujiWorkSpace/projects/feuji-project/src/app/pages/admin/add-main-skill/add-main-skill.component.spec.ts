import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog } from '@angular/material/dialog';
import { of } from 'rxjs';
import { AddMainSkillComponent } from './add-main-skill.component';
import { EmployeeSkillService } from '../../../services/employee-skill.service';

describe('AddMainSkillComponent', () => {
  let component: AddMainSkillComponent;
  let fixture: ComponentFixture<AddMainSkillComponent>;
  let employeeSkillServiceSpy: jasmine.SpyObj<EmployeeSkillService>;
  let dialogSpy: jasmine.SpyObj<MatDialog>;

  beforeEach(async () => {
    const employeeSkillServiceSpyObj = jasmine.createSpyObj('EmployeeSkillService', [
      'getSkillCategories',
      'getTechnicalCategory',
      'getSkills',
      'updateStatusAdmin',
      'deleteSubCategory',
      'deleteCategory'
    ]);

    const dialogSpyObj = jasmine.createSpyObj('MatDialog', ['open']);

    await TestBed.configureTestingModule({
      declarations: [AddMainSkillComponent],
      providers: [
        { provide: EmployeeSkillService, useValue: employeeSkillServiceSpyObj },
        { provide: MatDialog, useValue: dialogSpyObj }
      ]
    }).compileComponents();

    employeeSkillServiceSpy = TestBed.inject(EmployeeSkillService) as jasmine.SpyObj<EmployeeSkillService>;
    dialogSpy = TestBed.inject(MatDialog) as jasmine.SpyObj<MatDialog>;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddMainSkillComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load skill categories on initialization', () => {
    const mockCategories = [{ id: 1, name: 'Category A' }, { id: 2, name: 'Category B' }];
    employeeSkillServiceSpy.getSkillCategories.and.returnValue(of(mockCategories));

    component.ngOnInit();

    expect(employeeSkillServiceSpy.getSkillCategories).toHaveBeenCalled();
    expect(component.accordionData).toEqual(mockCategories);
  });

  const mockCategory = { id: 1, name: 'Category A' };

it('should handle selecting a skill category', () => {
    const mockSubSkills = [{ id: 1, name: 'Sub Skill 1' }, { id: 2, name: 'Sub Skill 2' }];

    employeeSkillServiceSpy.getTechnicalCategory.and.returnValue(of(mockSubSkills));

    component.onSelectSkillCategory(mockCategory);

    expect(employeeSkillServiceSpy.getTechnicalCategory).toHaveBeenCalledWith(mockCategory);
    expect(component.accordionSubData).toEqual(mockSubSkills);
    expect(component.selectedSkillCategory).toEqual(mockCategory.name); // Accessing the name property
});


const mockSkills: { id: number; name: string; }[] = [
  { id: 1, name: 'Skill 1' },
  { id: 2, name: 'Skill 2' },
];

it('should handle selecting a technical category', () => {
  const mockTechCat = 1;

  employeeSkillServiceSpy.getSkills.and.returnValue(of(mockSkills));

  component.onSelectTechCat(mockTechCat);

  expect(employeeSkillServiceSpy.getSkills).toHaveBeenCalledWith(mockTechCat);
  expect(component.allSkills).toEqual(mockSkills);
  expect(component.selectedSubItem).toBeDefined();
  expect(component.size).toEqual(mockSkills.length); 
});



});

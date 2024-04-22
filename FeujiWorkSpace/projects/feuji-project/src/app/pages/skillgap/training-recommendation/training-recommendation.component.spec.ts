import { TestBed, ComponentFixture } from '@angular/core/testing';
import { TrainingRecommendationComponent } from './training-recommendation.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import { of } from 'rxjs';
import { EmployeeSkillObject } from '../../../../models/EmployeeSkillObject.model';

describe('TrainingRecommendationComponent', () => {
  let component: TrainingRecommendationComponent;
  let fixture: ComponentFixture<TrainingRecommendationComponent>;
  let mockEmployeeSkillService: jasmine.SpyObj<EmployeeSkillService>;

  beforeEach(async () => {
    mockEmployeeSkillService = jasmine.createSpyObj('EmployeeSkillService', ['getSkillsByEmployeeId']);

    await TestBed.configureTestingModule({
      declarations: [TrainingRecommendationComponent],
      imports: [HttpClientTestingModule],
      providers: [
        { provide: EmployeeSkillService, useValue: mockEmployeeSkillService }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingRecommendationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should initialize component on ngOnInit', () => {
    const email = 'test@example.com';
    const mockSkills: EmployeeSkillObject[] = [
      {
          exReferenceDetailsValues: 'Beginner', acReferenceDetailsValues: 'Intermediate',
          firstName: '',
          middleName: '',
          lastName: '',
          employeeId: 0,
          email: '',
          designation: '',
          skillName: '',
          description: '',
          exCompetencyLevelId: 0,
          acCompetencyLevelId: 0,
          skillCategoryName: '',
          subSkillCategoryName: ''
      },
      {
          exReferenceDetailsValues: 'Intermediate', acReferenceDetailsValues: 'Expert',
          firstName: '',
          middleName: '',
          lastName: '',
          employeeId: 0,
          email: '',
          designation: '',
          skillName: '',
          description: '',
          exCompetencyLevelId: 0,
          acCompetencyLevelId: 0,
          skillCategoryName: '',
          subSkillCategoryName: ''
      }
    ];

    mockEmployeeSkillService.getSkillsByEmployeeId.and.returnValue(of(mockSkills));

    component.ngOnInit();

    expect(component.empMail).toEqual(email);
    expect(mockEmployeeSkillService.getSkillsByEmployeeId).toHaveBeenCalledWith(email);
    expect(component.empSkills).toEqual(mockSkills);
  });
});


describe('TrainingRecommendationComponent', () => {
    let component: TrainingRecommendationComponent;
    let fixture: ComponentFixture<TrainingRecommendationComponent>;
    let mockEmployeeSkillService: jasmine.SpyObj<EmployeeSkillService>;
  
    beforeEach(async () => {
      mockEmployeeSkillService = jasmine.createSpyObj('EmployeeSkillService', ['getSkillsByEmployeeId']);
  
      await TestBed.configureTestingModule({
        declarations: [TrainingRecommendationComponent],
        imports: [HttpClientTestingModule], 
        providers: [
          { provide: EmployeeSkillService, useValue: mockEmployeeSkillService }
        ]
      }).compileComponents();
    });
  
    beforeEach(() => {
      fixture = TestBed.createComponent(TrainingRecommendationComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  
    it('should initialize component on ngOnInit', () => {
      const email = 'test@example.com';
      const mockSkills: EmployeeSkillObject[] = [
        {
            exReferenceDetailsValues: 'Beginner', acReferenceDetailsValues: 'Intermediate',
            firstName: '',
            middleName: '',
            lastName: '',
            employeeId: 0,
            email: '',
            designation: '',
            skillName: '',
            description: '',
            exCompetencyLevelId: 0,
            acCompetencyLevelId: 0,
            skillCategoryName: '',
            subSkillCategoryName: ''
        },
        {
            exReferenceDetailsValues: 'Intermediate', acReferenceDetailsValues: 'Expert',
            firstName: '',
            middleName: '',
            lastName: '',
            employeeId: 0,
            email: '',
            designation: '',
            skillName: '',
            description: '',
            exCompetencyLevelId: 0,
            acCompetencyLevelId: 0,
            skillCategoryName: '',
            subSkillCategoryName: ''
        }
      ];
  
      mockEmployeeSkillService.getSkillsByEmployeeId.and.returnValue(of(mockSkills));
  
      component.ngOnInit();
  
      expect(component.empMail).toEqual(email);
      expect(mockEmployeeSkillService.getSkillsByEmployeeId).toHaveBeenCalledWith(email);
      expect(component.empSkills).toEqual(mockSkills);
    });
  
    it('should generate correct difference for skill levels', () => {
      const exLevel = 'Beginner';
      const acLevel = 'Intermediate';
  
      const result = component.generateResult(exLevel, acLevel);
  
      expect(result).toEqual(-1); 
    });
  
    it('should handle unexpected skill levels', () => {
      const exLevel = 'Advanced';
      const acLevel = 'Expert';
  
      spyOn(console, 'warn'); 
  
      const result = component.generateResult(exLevel, acLevel);
  
      expect(result).toEqual(-1); 
      expect(console.warn).toHaveBeenCalledTimes(2);  
    });
  });

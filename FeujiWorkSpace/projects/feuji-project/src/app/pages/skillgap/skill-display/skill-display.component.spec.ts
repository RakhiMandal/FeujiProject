import { TestBed, ComponentFixture, tick, fakeAsync } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SkillDisplayComponent } from './skill-display.component';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import { Skill } from '../../../../models/skill.model';
import { SkillData } from '../../../../models/SkillData.service';
import { of } from 'rxjs';
import Swal from 'sweetalert2';

describe('SkillDisplayComponent', () => {
  let component: SkillDisplayComponent;
  let fixture: ComponentFixture<SkillDisplayComponent>;
  let mockDialogRef: MatDialogRef<SkillDisplayComponent>;
  let mockEmployeeSkillService: EmployeeSkillService;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SkillDisplayComponent],
      imports: [HttpClientTestingModule],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: { selectedSubSkillCategory: 1, selectedSkillCategory: 'CategoryName' } },
        { provide: MatDialogRef, useValue: { close: () => {} } },
        SkillData
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(SkillDisplayComponent);
    component = fixture.componentInstance;
    mockDialogRef = TestBed.inject(MatDialogRef);
    mockEmployeeSkillService = TestBed.inject(EmployeeSkillService);
    httpTestingController = TestBed.inject(HttpTestingController);

    fixture.detectChanges();
  });

  afterEach(() => {
    httpTestingController.verify();  
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should call saveSkill method and close dialog on successful skill save', fakeAsync(() => {
    const response = { referenceTypeId: 123 };
    const skillBean: Skill = new Skill('NewSkill', 1, response.referenceTypeId, 'Skill Description', 1, 'Admin', 'Admin');

    spyOn(mockEmployeeSkillService, 'getSkillCategoryTypeId').and.returnValue(of(response));

    component.newSkillName = 'NewSkill';
    component.newSkillDescription = 'Skill Description';
    component.selectedSubSkillCategoryId = 1;
    component.skillCategoryName = 'CategoryName';

    component.saveSkill();
    tick();

    expect(mockEmployeeSkillService.getSkillCategoryTypeId).toHaveBeenCalledWith('CategoryName');
    expect(mockEmployeeSkillService.saveSkillAdmin).toHaveBeenCalledWith(skillBean);
    expect(mockDialogRef.close).toHaveBeenCalled();
    expect(Swal.fire).toHaveBeenCalledWith(jasmine.objectContaining({
      title: 'Skill saved successfully',
      icon: 'success'
    }));
  }));

  it('should handle error on skill save', fakeAsync(() => {
    const response = { referenceTypeId: 123 };

    spyOn(mockEmployeeSkillService, 'getSkillCategoryTypeId').and.returnValue(of(response));

    component.newSkillName = 'NewSkill';
    component.newSkillDescription = 'Skill Description';
    component.selectedSubSkillCategoryId = 1;
    component.skillCategoryName = 'CategoryName';

    component.saveSkill();
    tick();

    expect(mockEmployeeSkillService.getSkillCategoryTypeId).toHaveBeenCalledWith('CategoryName');
    expect(mockEmployeeSkillService.saveSkillAdmin).toHaveBeenCalledWith(jasmine.any(Skill));
    expect(mockDialogRef.close).toHaveBeenCalled();
    expect(Swal.fire).toHaveBeenCalledWith(jasmine.objectContaining({
      title: 'Skill saved successfully',
      icon: 'success'
    }));
  }));

});

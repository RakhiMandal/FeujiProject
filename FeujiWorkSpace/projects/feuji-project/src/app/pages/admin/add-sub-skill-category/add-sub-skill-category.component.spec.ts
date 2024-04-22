import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { of, throwError } from 'rxjs';
import { AddSubSkillCategoryComponent } from './add-sub-skill-category.component';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import { SubSkillCategoryBean } from '../../../../models/SubSkillCategoryBean.model';
import Swal from 'sweetalert2';

describe('AddSubSkillCategoryComponent', () => {
  let component: AddSubSkillCategoryComponent;
  let fixture: ComponentFixture<AddSubSkillCategoryComponent>;
  let dialogRefSpy: jasmine.SpyObj<MatDialogRef<AddSubSkillCategoryComponent>>;
  let employeeSkillServiceSpy: jasmine.SpyObj<EmployeeSkillService>;

  beforeEach(() => {
    const dialogRefSpyObj = jasmine.createSpyObj('MatDialogRef', ['close']);
    const employeeSkillServiceSpyObj = jasmine.createSpyObj('EmployeeSkillService', ['getSkillCategoryTypeId', 'saveSubSkillCategoryAdmin']);

    TestBed.configureTestingModule({
      declarations: [AddSubSkillCategoryComponent],
      providers: [
        { provide: MatDialogRef, useValue: dialogRefSpyObj },
        { provide: MAT_DIALOG_DATA, useValue: { selectedsubskillcategory: 'SampleCategory' } },
        { provide: EmployeeSkillService, useValue: employeeSkillServiceSpyObj }
      ]
    });

    fixture = TestBed.createComponent(AddSubSkillCategoryComponent);
    component = fixture.componentInstance;
    dialogRefSpy = TestBed.inject(MatDialogRef) as jasmine.SpyObj<MatDialogRef<AddSubSkillCategoryComponent>>;
    employeeSkillServiceSpy = TestBed.inject(EmployeeSkillService) as jasmine.SpyObj<EmployeeSkillService>;
  });

  it('should save sub-skill category successfully', () => {
    
    const mockResponse = { referenceTypeId: 123 };
    const mockSubSkillCategoryName = 'New Sub Skill Category';
    const expectedSubSkillCategoryBean = new SubSkillCategoryBean(
      mockSubSkillCategoryName,
      { referenceTypeId: mockResponse.referenceTypeId, referenceTypeName: mockSubSkillCategoryName },
      'Admin',
      'Admin'
    );

    component.newSubSkillCategoryName = mockSubSkillCategoryName;
    employeeSkillServiceSpy.getSkillCategoryTypeId.and.returnValue(of(mockResponse));
    employeeSkillServiceSpy.saveSubSkillCategoryAdmin.and.returnValue(of(expectedSubSkillCategoryBean));

    component.saveSubSkillCategory();

    expect(employeeSkillServiceSpy.getSkillCategoryTypeId).toHaveBeenCalledWith('SampleCategory');
    expect(employeeSkillServiceSpy.saveSubSkillCategoryAdmin).toHaveBeenCalledWith(expectedSubSkillCategoryBean);
    expect(Swal.fire).toHaveBeenCalledWith({
      title: 'Sub Skill category saved successfully',
      icon: 'success'
    } as any); 

    expect(dialogRefSpy.close).toHaveBeenCalled();
  });

  it('should handle error when saving sub-skill category', () => {
   
    component.newSubSkillCategoryName = 'Invalid Sub Skill Category';
    employeeSkillServiceSpy.getSkillCategoryTypeId.and.returnValue(of({}));
    employeeSkillServiceSpy.saveSubSkillCategoryAdmin.and.returnValue(throwError('Error'));

    
    component.saveSubSkillCategory();

   
    expect(employeeSkillServiceSpy.saveSubSkillCategoryAdmin).toHaveBeenCalled();
    expect(Swal.fire).toHaveBeenCalledWith({
      title: 'Sub Skill already present',
      icon: 'error'
    } as any); 
    expect(dialogRefSpy.close).toHaveBeenCalled();
  });
});

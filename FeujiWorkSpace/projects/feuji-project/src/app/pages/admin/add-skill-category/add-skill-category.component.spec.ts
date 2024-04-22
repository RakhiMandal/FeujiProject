import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef } from '@angular/material/dialog';
import { of } from 'rxjs';
import { AddSkillCategoryComponent } from './add-skill-category.component';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import { ReferenceSkillCategoryBean } from '../../../../models/ReferenceSkillCategoryBean';
import { SkillCategoryBean } from '../../../../models/SkillCategoryBean.model';

describe('AddSkillCategoryComponent', () => {
  let component: AddSkillCategoryComponent;
  let fixture: ComponentFixture<AddSkillCategoryComponent>;
  let dialogRefSpy: jasmine.SpyObj<MatDialogRef<AddSkillCategoryComponent>>;
  let employeeSkillServiceSpy: jasmine.SpyObj<EmployeeSkillService>;

  beforeEach(() => {
    const dialogRefSpyObj = jasmine.createSpyObj('MatDialogRef', ['close']);
    const employeeSkillServiceSpyObj = jasmine.createSpyObj('EmployeeSkillService', [
      'saveSkillCategoryAdmin'
    ]);

    TestBed.configureTestingModule({
      declarations: [AddSkillCategoryComponent],
      providers: [
        { provide: MatDialogRef, useValue: dialogRefSpyObj },
        { provide: EmployeeSkillService, useValue: employeeSkillServiceSpyObj }
      ]
    });

    fixture = TestBed.createComponent(AddSkillCategoryComponent);
    component = fixture.componentInstance;
    dialogRefSpy = TestBed.inject(MatDialogRef) as jasmine.SpyObj<MatDialogRef<AddSkillCategoryComponent>>;
    employeeSkillServiceSpy = TestBed.inject(EmployeeSkillService) as jasmine.SpyObj<EmployeeSkillService>;
  });

  it('should save skill category successfully', () => {
    const mockCategoryName = 'New Category';
    const mockReferenceTypeId = new ReferenceSkillCategoryBean(1, 'Category Type'); // Provide valid values
    const mockIsDeleted = 0;
    const mockCreatedBy = 'Admin';
    const mockModifiedBy = 'Admin';
    component.newSkillCategoryName = mockCategoryName;
    const mockSkillCategory = new SkillCategoryBean(
      mockCategoryName,
      mockReferenceTypeId,
      mockIsDeleted,
      mockCreatedBy,
      mockModifiedBy
    );
    employeeSkillServiceSpy.saveSkillCategoryAdmin.and.returnValue(of(mockSkillCategory));
    component.saveSkillCategory();
    expect(employeeSkillServiceSpy.saveSkillCategoryAdmin).toHaveBeenCalled();
    expect(employeeSkillServiceSpy.saveSkillCategoryAdmin).toHaveBeenCalledWith(mockSkillCategory); // Expect with specific argument
    expect(component.fetchSkillCategories).toHaveBeenCalled(); // Assuming fetchSkillCategories is called after successful save
    expect(dialogRefSpy.close).toHaveBeenCalled(); // Ensure the dialog is closed
  });

    beforeEach(() => {
      const dialogRefSpyObj = jasmine.createSpyObj('MatDialogRef', ['close']);
      const employeeSkillServiceSpyObj = jasmine.createSpyObj('EmployeeSkillService', [
        'getSkillCategoryNames',
        'getSkillCategoryIdByName',
        'saveSkillCategoryAdmin'
      ]);
  
      TestBed.configureTestingModule({
        declarations: [AddSkillCategoryComponent],
        providers: [
          { provide: MatDialogRef, useValue: dialogRefSpyObj },
          { provide: EmployeeSkillService, useValue: employeeSkillServiceSpyObj }
        ]
      });
  
      fixture = TestBed.createComponent(AddSkillCategoryComponent);
      component = fixture.componentInstance;
      dialogRefSpy = TestBed.inject(MatDialogRef) as jasmine.SpyObj<MatDialogRef<AddSkillCategoryComponent>>;
      employeeSkillServiceSpy = TestBed.inject(EmployeeSkillService) as jasmine.SpyObj<EmployeeSkillService>;
      employeeSkillServiceSpy.getSkillCategoryNames.and.returnValue(of([]));
      employeeSkillServiceSpy.getSkillCategoryIdByName.and.returnValue(of({ referenceTypeId: 1 })); // Mock response for referenceTypeId
    });
  
    it('should save skill category successfully', () => {
      const mockCategoryName = 'New Category';
      const mockReferenceTypeId = new ReferenceSkillCategoryBean(1, 'Category Type');
      const mockIsDeleted = 0;
      const mockCreatedBy = 'Admin';
      const mockModifiedBy = 'Admin';
      component.newSkillCategoryName = mockCategoryName;
      const mockSkillCategory = new SkillCategoryBean(
        mockCategoryName,
        mockReferenceTypeId,
        mockIsDeleted,
        mockCreatedBy,
        mockModifiedBy
      );
      employeeSkillServiceSpy.saveSkillCategoryAdmin.and.returnValue(of(mockSkillCategory));
      component.saveSkillCategory();
      expect(employeeSkillServiceSpy.saveSkillCategoryAdmin).toHaveBeenCalled();
      expect(employeeSkillServiceSpy.saveSkillCategoryAdmin).toHaveBeenCalledWith(mockSkillCategory);
      expect(component.fetchSkillCategories).toHaveBeenCalled();
      expect(dialogRefSpy.close).toHaveBeenCalled();
    });
});

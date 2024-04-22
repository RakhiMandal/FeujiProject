import { ComponentFixture, TestBed, async } from "@angular/core/testing";
import { EmployeeSkillDisplayComponent } from "./employee-skill-display.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { MatDialogModule } from "@angular/material/dialog";
import { of } from "rxjs";
import { EmployeeSkillService } from "../../../services/employee-skill.service";


describe('EmployeeSkillDisplayComponent', () => {
  let component: EmployeeSkillDisplayComponent;
  let fixture: ComponentFixture<EmployeeSkillDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [EmployeeSkillDisplayComponent],
      imports: [HttpClientTestingModule, MatDialogModule], // Import necessary modules
      providers: [EmployeeSkillService]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeSkillDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize component properties on ngOnInit', () => {
    const email = 'test@example.com';
    spyOn(localStorage, 'getItem').and.returnValue(JSON.stringify(email));

    component.ngOnInit();

    expect(component.empMail).toEqual(email);
    expect(component.isLoading).toBeTrue();

    const skillCategories = ['Category1', 'Category2'];
    const skillCategoryInput = 'Skill Category';
    const empskillService = TestBed.inject(EmployeeSkillService);

    spyOn(empskillService, 'getSkillCategories').and.returnValue(of(skillCategories));

    component.ngOnInit();

    expect(component.skillCategories).toEqual(skillCategories);
    expect(empskillService.getSkillCategories).toHaveBeenCalledWith(skillCategoryInput);
  });
});

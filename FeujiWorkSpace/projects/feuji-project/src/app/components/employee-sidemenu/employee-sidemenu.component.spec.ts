import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeSidemenuComponent } from './employee-sidemenu.component';

describe('EmployeeSidemenuComponent', () => {
  let component: EmployeeSidemenuComponent;
  let fixture: ComponentFixture<EmployeeSidemenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmployeeSidemenuComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EmployeeSidemenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

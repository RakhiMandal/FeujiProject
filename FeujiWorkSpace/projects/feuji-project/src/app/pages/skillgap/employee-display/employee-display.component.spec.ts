import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeDisplayComponent } from './employee-display.component';

describe('EmployeeDisplayComponent', () => {
  let component: EmployeeDisplayComponent;
  let fixture: ComponentFixture<EmployeeDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmployeeDisplayComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EmployeeDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

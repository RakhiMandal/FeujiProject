import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditholidayComponent } from './editholiday-component.component';

describe('EditholidayComponentComponent', () => {
  let component: EditholidayComponent;
  let fixture: ComponentFixture<EditholidayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditholidayComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditholidayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

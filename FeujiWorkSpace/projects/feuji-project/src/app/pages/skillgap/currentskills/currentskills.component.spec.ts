import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrentskillsComponent } from './currentskills.component';

describe('CurrentskillsComponent', () => {
  let component: CurrentskillsComponent;
  let fixture: ComponentFixture<CurrentskillsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CurrentskillsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CurrentskillsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

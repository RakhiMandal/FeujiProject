import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateEmlpoyeeComponent } from './update-emlpoyee.component';

describe('UpdateEmlpoyeeComponent', () => {
  let component: UpdateEmlpoyeeComponent;
  let fixture: ComponentFixture<UpdateEmlpoyeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UpdateEmlpoyeeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UpdateEmlpoyeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

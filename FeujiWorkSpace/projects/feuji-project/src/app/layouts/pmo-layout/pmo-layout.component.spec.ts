import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PmoLayoutComponent } from './pmo-layout.component';

describe('PmoLayoutComponent', () => {
  let component: PmoLayoutComponent;
  let fixture: ComponentFixture<PmoLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PmoLayoutComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PmoLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { HolidayserviceService } from './holidayservice.service';

describe('HolidayserviceService', () => {
  let service: HolidayserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HolidayserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

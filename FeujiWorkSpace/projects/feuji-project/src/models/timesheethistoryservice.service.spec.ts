import { TestBed } from '@angular/core/testing';

import { TimesheethistoryserviceService } from './timesheethistoryservice.service';

describe('TimesheethistoryserviceService', () => {
  let service: TimesheethistoryserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TimesheethistoryserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

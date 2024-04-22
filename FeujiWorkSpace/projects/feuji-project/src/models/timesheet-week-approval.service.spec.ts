import { TestBed } from '@angular/core/testing';

import { TimesheetWeekApprovalService } from './timesheet-week-approval.service';

describe('TimesheetWeekApprovalService', () => {
  let service: TimesheetWeekApprovalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TimesheetWeekApprovalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

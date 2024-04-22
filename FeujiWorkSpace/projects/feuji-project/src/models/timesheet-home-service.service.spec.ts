
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TimesheetHomeService } from './timesheetHomeService.service';

describe('TimesheetHomeService', () => {
  let service: TimesheetHomeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TimesheetHomeService],
    });
    service = TestBed.inject(TimesheetHomeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});


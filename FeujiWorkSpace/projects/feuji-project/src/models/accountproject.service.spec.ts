import { TestBed } from '@angular/core/testing';

import { AccountprojectService } from './accountproject.service';

describe('AccountprojectService', () => {
  let service: AccountprojectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccountprojectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

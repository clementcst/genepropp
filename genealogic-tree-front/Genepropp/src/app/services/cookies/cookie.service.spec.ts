import { TestBed } from '@angular/core/testing';

import { CookieManagementService } from './cookie.service';

describe('CookieService', () => {
  let service: CookieManagementService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CookieManagementService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { DemoFormTableService } from './demo-form-table.service';

describe('DemoFormTableService', () => {
  let service: DemoFormTableService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DemoFormTableService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

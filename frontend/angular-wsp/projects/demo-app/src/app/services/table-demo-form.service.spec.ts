import { TestBed } from '@angular/core/testing';

import { TableDemoFormService } from './table-demo-form.service';

describe('DemoFormTableService', () => {
  let service: TableDemoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TableDemoFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

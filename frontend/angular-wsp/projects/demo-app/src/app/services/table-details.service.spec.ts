import { TestBed } from '@angular/core/testing';

import { TableDetailsService } from './table-details.service';

describe('TableDetailsService', () => {
  let service: TableDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TableDetailsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

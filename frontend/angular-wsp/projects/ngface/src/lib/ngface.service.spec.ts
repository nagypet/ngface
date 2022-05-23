import { TestBed } from '@angular/core/testing';

import { NgfaceService } from './ngface.service';

describe('NgfaceService', () => {
  let service: NgfaceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NgfaceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

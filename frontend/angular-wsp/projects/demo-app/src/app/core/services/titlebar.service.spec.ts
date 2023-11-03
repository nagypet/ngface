import { TestBed } from '@angular/core/testing';

import { TitlebarService } from './titlebar.service';

describe('TitlebarService', () => {
  let service: TitlebarService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TitlebarService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

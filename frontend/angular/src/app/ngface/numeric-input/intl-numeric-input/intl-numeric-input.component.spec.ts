import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IntlNumericInputComponent } from './intl-numeric-input.component';

describe('IntlNumericInputComponent', () => {
  let component: IntlNumericInputComponent;
  let fixture: ComponentFixture<IntlNumericInputComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IntlNumericInputComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IntlNumericInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

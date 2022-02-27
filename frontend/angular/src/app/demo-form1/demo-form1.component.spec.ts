import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemoForm1Component } from './demo-form1.component';

describe('DemoForm1Component', () => {
  let component: DemoForm1Component;
  let fixture: ComponentFixture<DemoForm1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DemoForm1Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DemoForm1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

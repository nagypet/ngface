import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemoDialog1Component } from './demo-dialog1.component';

describe('DemoDialog1Component', () => {
  let component: DemoDialog1Component;
  let fixture: ComponentFixture<DemoDialog1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DemoDialog1Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DemoDialog1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableDemoFormComponent } from './table-demo-form.component';

describe('DemoForm2Component', () => {
  let component: TableDemoFormComponent;
  let fixture: ComponentFixture<TableDemoFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableDemoFormComponent]
    });
    fixture = TestBed.createComponent(TableDemoFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

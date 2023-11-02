import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NgfaceTitlebarComponent } from './ngface-titlebar.component';

describe('NgfaceTitlebarComponent', () => {
  let component: NgfaceTitlebarComponent;
  let fixture: ComponentFixture<NgfaceTitlebarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NgfaceTitlebarComponent]
    });
    fixture = TestBed.createComponent(NgfaceTitlebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NgfaceFileUploadComponent } from './ngface-file-upload.component';

describe('NgfaceFileuploadComponent', () => {
  let component: NgfaceFileUploadComponent;
  let fixture: ComponentFixture<NgfaceFileUploadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NgfaceFileUploadComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NgfaceFileUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

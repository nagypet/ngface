import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NgfaceAutocompleteComponent } from './ngface-autocomplete.component';

describe('NgfaceAutocompleteComponent', () => {
  let component: NgfaceAutocompleteComponent;
  let fixture: ComponentFixture<NgfaceAutocompleteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NgfaceAutocompleteComponent]
    });
    fixture = TestBed.createComponent(NgfaceAutocompleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

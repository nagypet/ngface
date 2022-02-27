import {Component, Input} from '@angular/core';
import {FormGroup} from '@angular/forms';

@Component({
  selector: 'ngface-form-base',
  template: ''
})
export abstract class FormBaseComponent
{
  @Input()
  formGroup: FormGroup;

  constructor()
  {
  }
}

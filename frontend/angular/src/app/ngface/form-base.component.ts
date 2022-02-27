import {Component, Input} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {TypeModels} from '../dto-models';
import Form = TypeModels.Form;

@Component({
  selector: 'ngface-form-base',
  template: ''
})
export abstract class FormBaseComponent
{
  formData: Form;
  formGroup = new FormGroup({});

  constructor()
  {
  }
}

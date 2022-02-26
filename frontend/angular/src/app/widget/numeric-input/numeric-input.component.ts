import {Component, OnChanges, OnInit} from '@angular/core';
import {TypeModels} from '../../dto-models';
import {InputBaseComponent} from '../input-base.component';
import NumericInput = TypeModels.NumericInput;

@Component({
  selector: 'ngface-numeric-input',
  templateUrl: './numeric-input.component.html',
  styleUrls: ['./numeric-input.component.scss']
})
export class NumericInputComponent extends InputBaseComponent implements OnInit, OnChanges
{
  constructor()
  {
    super();
  }

  ngOnInit(): void
  {
  }

  ngOnChanges(): void
  {
    super.ngOnChanges();
    this.formControl.setValue(this.getValue());
  }

  getData(): NumericInput
  {
    let widget = this.form?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'NumericInput')
    {
      return {
        type: 'TextInput',
        precision: 0,
        value: 0,
        prefix: '',
        suffix: '',
        placeholder: 'widget id: ' + this.widgetId,
        label: 'undefined label',
        validators: [],
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return <NumericInput> this.form.widgets[this.widgetId];
  }

  getValue(): string | undefined
  {
    return this.getData()?.value?.toFixed(this.getData()?.precision);
  }
}

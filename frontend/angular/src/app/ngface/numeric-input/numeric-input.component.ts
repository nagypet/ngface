import {Component, OnChanges, OnInit} from '@angular/core';
import {TypeModels} from '../../dto-models';
import {InputBaseComponent} from '../input-base.component';
import NumericInput = TypeModels.NumericInput;

@Component({
  selector: 'ngface-numeric-input',
  templateUrl: './numeric-input.component.html',
  styleUrls: ['./numeric-input.component.scss']
})
export class NumericInputComponent extends InputBaseComponent
{
  constructor()
  {
    super();
  }

  getData(): NumericInput
  {
    let widget = this.formData?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'NumericInput')
    {
      return {
        type: 'NumericInput',
        data: {type: 'NumericInput.Data', value: 0},
        precision: 0,
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
    return <NumericInput> this.formData.widgets[this.widgetId];
  }

  getValue(): string | undefined
  {
    return this.getData()?.data?.value?.toFixed(this.getData()?.precision);
  }
}

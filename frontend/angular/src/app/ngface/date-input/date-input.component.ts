import {Component, OnInit} from '@angular/core';
import {InputBaseComponent} from '../input-base.component';
import {TypeModels} from '../../dto-models';
import DateInput = TypeModels.DateInput;

@Component({
  selector: 'ngface-date-input',
  templateUrl: './date-input.component.html',
  styleUrls: ['./date-input.component.scss']
})
export class DateInputComponent extends InputBaseComponent
{

  constructor()
  {
    super();
  }

  getData(): DateInput
  {
    let widget = this.formData?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'DateInput')
    {
      return {
        type: 'DateInput',
        data: {type: 'DateInput.Data', value: new Date()},
        placeholder: 'widget id: ' + this.widgetId,
        label: 'undefined label',
        validators: [],
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return <DateInput> this.formData.widgets[this.widgetId];
  }
}

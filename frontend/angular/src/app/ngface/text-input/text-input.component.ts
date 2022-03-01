import {Component, OnChanges, OnInit} from '@angular/core';
import {TypeModels} from '../../dto-models';
import {InputBaseComponent} from '../input-base.component';
import TextInput = TypeModels.TextInput;

@Component({
  selector: 'ngface-text-input',
  templateUrl: './text-input.component.html',
  styleUrls: ['./text-input.component.scss']
})
export class TextInputComponent extends InputBaseComponent
{

  constructor()
  {
    super();
  }

  getData(): TextInput
  {
    let widget = this.formData?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'TextInput')
    {
      return {
        type: 'TextInput',
        data: {type: 'TextInput.Data', value: ''},
        placeholder: 'widget id: ' + this.widgetId,
        label: 'undefined label',
        validators: [],
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return <TextInput> this.formData.widgets[this.widgetId];
  }
}

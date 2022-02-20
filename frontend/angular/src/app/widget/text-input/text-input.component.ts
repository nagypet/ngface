import {Component, Input, OnInit} from '@angular/core';
import {TypeModels} from '../../dto-models';
import TextInput = TypeModels.TextInput;
import Form = TypeModels.Form;

@Component({
  selector: 'text-input',
  templateUrl: './text-input.component.html',
  styleUrls: ['./text-input.component.scss']
})
export class TextInputComponent implements OnInit {

  @Input()
  form: Form;

  @Input()
  widgetId: string;

  constructor() {
  }

  ngOnInit(): void {
  }

  getData() : TextInput
  {
    let widget = this.form?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'TextInput')
    {
      return {
        type: 'TextInput',
        placeholder: 'widget id: ' + this.widgetId,
        label: 'undefined label',
        value: '',
        constraints: [],
        enabled: false,
        id: '',
        tooltip: 'undefined tooltip'
        };
    }
    return <TextInput> this.form.widgets[this.widgetId];
  }

  isRequired(): boolean {
    return !!this.getData().constraints?.find(c => c.type == 'Required');
  }

}

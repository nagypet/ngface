import {Component, OnChanges, OnInit} from '@angular/core';
import {TypeModels} from '../../dto-models';
import {InputBaseComponent} from '../input-base.component';
import TextInput = TypeModels.TextInput;

@Component({
  selector: 'ngface-text-input',
  templateUrl: './text-input.component.html',
  styleUrls: ['./text-input.component.scss']
})
export class TextInputComponent extends InputBaseComponent implements OnInit, OnChanges
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
    this.formControl.setValue(this.getData().value);
  }

  getData(): TextInput
  {
    let widget = this.form?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'TextInput')
    {
      return {
        type: 'TextInput',
        placeholder: 'widget id: ' + this.widgetId,
        label: 'undefined label',
        value: '',
        validators: [],
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return <TextInput> this.form.widgets[this.widgetId];
  }
}

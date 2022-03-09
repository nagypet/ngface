import {Component, Input, OnInit} from '@angular/core';
import {TypeModels} from '../../dto-models';
import Form = TypeModels.Form;
import Button = TypeModels.Button;

@Component({
  selector: 'ngface-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss']
})
export class ButtonComponent implements OnInit
{

  @Input()
  formData: Form;

  @Input()
  widgetId: string;

  constructor()
  {
  }

  ngOnInit(): void
  {
  }

  getData(): Button
  {
    let widget = this.formData?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'Button')
    {
      return {
        data: {type: 'VoidWidgetData'},
        style: 'NONE',
        type: '',
        label: 'undefined button',
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return <Button> this.formData.widgets[this.widgetId];
  }

  onClick()
  {
    console.log('Button clicked');
  }

  getClass()
  {
    return {
      '': this.getData().style === 'NONE',
      'mat-primary': this.getData().style === 'PRIMARY',
      'mat-accent': this.getData().style === 'ACCENT',
      'mat-warn': this.getData().style === 'WARN'
    };
  }
}

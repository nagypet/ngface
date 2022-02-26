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
  form: Form;

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
    let widget = this.form?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'Button')
    {
      return {
        style: 'NONE',
        type: '',
        label: 'undefined button',
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return <Button> this.form.widgets[this.widgetId];
  }

  onClick()
  {
    console.log('clicked');
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

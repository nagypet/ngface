import {Component} from '@angular/core';
import {Ngface} from './ngface-models';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent
{
  formDataJson: string;
  title = 'angular-demo';


  constructor()
  {
    let buttonOk: Ngface.Button = {
      id: 'button-ok',
      type: 'Button',
      style: 'PRIMARY',
      label: 'OK',
      enabled: true,
      hint: 'This is the OK button',
      badge: '',
      data: {type: 'VoidWidgetData'}
    };
    let formData: Ngface.Form = {id: 'demo', title: 'title', widgets: {'button-ok': buttonOk}};
    this.formDataJson = JSON.stringify(formData);
  }
}

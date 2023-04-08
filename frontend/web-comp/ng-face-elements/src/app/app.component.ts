import {Component} from '@angular/core';
import {Ngface} from './ngface-models';
import Form = Ngface.Form;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent
{
  formData: Form;
  title = 'ng-face-elements';


  constructor()
  {
    this.formData = {id: 'demo', title: 'title', widgets: {}};
  }
}

import {Component} from '@angular/core';
import {FormBaseComponent} from '../../../ngface/src/lib/form-base.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent extends FormBaseComponent
{
  title = 'ngface-elements';

  constructor()
  {
    super();
    this.formData = {id: 'demo', title: 'title', widgets: {}};
  }
}

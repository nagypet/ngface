import {Component} from '@angular/core';
import {FormBaseComponent} from '../../../ngface/src/lib/form/form-base.component';
import { NgfaceButtonComponent } from '../../../ngface/src/lib/widgets/ngface-button/ngface-button.component';
import { NgfaceTextInputComponent } from '../../../ngface/src/lib/widgets/ngface-text-input/ngface-text-input.component';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
    standalone: true,
    imports: [NgfaceTextInputComponent, NgfaceButtonComponent]
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

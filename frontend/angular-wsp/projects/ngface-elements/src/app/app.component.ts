/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

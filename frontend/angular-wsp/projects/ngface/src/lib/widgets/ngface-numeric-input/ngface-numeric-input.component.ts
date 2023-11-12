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

import {Component, Inject, LOCALE_ID} from '@angular/core';
import {Ngface} from '../../ngface-models';
import {InputBaseComponent} from '../input-base.component';
import {ReactiveFormsModule} from '@angular/forms';
import {IntlNumericInputComponent} from './intl-numeric-input/intl-numeric-input.component';
import {ResponsiveClassDirective} from '../../directives/responsive-class-directive';

@Component({
    // tslint:disable-next-line:component-selector
    selector: 'ngface-numeric-input',
    templateUrl: './ngface-numeric-input.component.html',
    standalone: true,
    imports: [IntlNumericInputComponent, ReactiveFormsModule, ResponsiveClassDirective]
})
export class NgfaceNumericInputComponent extends InputBaseComponent
{
  constructor(@Inject(LOCALE_ID) public locale: string)
  {
    super();
  }


  getData(): Ngface.NumericInput
  {
    const widget = this.formdata?.widgets[this.widgetid];
    if (!widget || widget?.type !== 'NumericInput')
    {
      return {
        type: 'NumericInput',
        data: {type: 'NumericInput.Data', value: 0},
        format: {precision: 0, prefix: '', suffix: '', digitGrouping: true, validators: []},
        placeholder: 'widget id: ' + this.widgetid,
        label: 'undefined label',
        validators: [],
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return this.formdata?.widgets[this.widgetid] as Ngface.NumericInput;
  }
}

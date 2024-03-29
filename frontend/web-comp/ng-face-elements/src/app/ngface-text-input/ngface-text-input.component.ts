/*
 * Copyright 2020-2022 the original author or authors.
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
import {Ngface} from '../ngface-models';
import {InputBaseComponent} from '../input-base.component';
import TextInput = Ngface.TextInput;

@Component({
  selector: 'ngface-text-input',
  templateUrl: './ngface-text-input.component.html',
  styleUrls: ['./ngface-text-input.component.scss']
})
export class NgfaceTextInputComponent extends InputBaseComponent
{

  constructor()
  {
    super();
  }

  getData(): TextInput
  {
    let widget = this.formData?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'TextInput')
    {
      return {
        type: 'TextInput',
        data: {type: 'TextInput.Data', value: ''},
        placeholder: 'widget id: ' + this.widgetId,
        label: 'undefined label',
        validators: [],
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return this.formData?.widgets[this.widgetId] as TextInput;
  }
}

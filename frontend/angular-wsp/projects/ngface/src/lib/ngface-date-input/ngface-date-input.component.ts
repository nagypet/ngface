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
import {InputBaseComponent} from '../input-base.component';
import {Ngface} from '../ngface-models';
import DateInput = Ngface.DateInput;

@Component({
  selector: 'ngface-date-input',
  templateUrl: './ngface-date-input.component.html',
  styleUrls: ['./ngface-date-input.component.scss']
})
export class NgfaceDateInputComponent extends InputBaseComponent
{

  constructor()
  {
    super();
  }

  getData(): DateInput
  {
    let widget = this.formData?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'DateInput')
    {
      return {
        type: 'DateInput',
        data: {type: 'DateInput.Data', value: new Date()},
        placeholder: 'widget id: ' + this.widgetId,
        label: 'undefined label',
        validators: [],
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return <DateInput> this.formData.widgets[this.widgetId];
  }
}

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
import {TypeModels} from '../../dto-models';
import {InputBaseComponent} from '../input-base.component';
import NumericInput = TypeModels.NumericInput;

@Component({
  selector: 'ngface-numeric-input',
  templateUrl: './numeric-input.component.html',
  styleUrls: ['./numeric-input.component.scss']
})
export class NumericInputComponent extends InputBaseComponent
{
  constructor()
  {
    super();
  }

  onChange()
  {
    let formattedValue = this.getFormattedValueAsText(this.formControl.value);
    if (this.formControl.value != formattedValue)
    {
      this.formControl.setValue(formattedValue);
    }
  }

  getData(): NumericInput
  {
    let widget = this.formData?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'NumericInput')
    {
      return {
        type: 'NumericInput',
        data: {type: 'NumericInput.Data', value: 0},
        precision: 0,
        prefix: '',
        suffix: '',
        placeholder: 'widget id: ' + this.widgetId,
        label: 'undefined label',
        validators: [],
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return <NumericInput> this.formData.widgets[this.widgetId];
  }

  getValue(): string
  {
    return this.getFormattedValueAsText(this.getData()?.data?.value);
  }

  getFormattedValueAsText(value: number): string
  {
    return Number(value).toFixed(this.getData()?.precision);
  }

  onFocusOut()
  {
    this.formControl.setValue(this.getFormattedValueAsText(this.formControl.value));
  }
}

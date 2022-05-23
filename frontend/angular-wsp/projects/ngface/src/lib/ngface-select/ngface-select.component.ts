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

import {Component, OnChanges} from '@angular/core';
import {InputBaseComponent} from '../input-base.component';
import {TypeModels} from '../dto-models';

@Component({
  selector: 'ngface-select',
  templateUrl: './ngface-select.component.html',
  styleUrls: ['./ngface-select.component.scss']
})
export class NgfaceSelectComponent extends InputBaseComponent implements OnChanges {

  constructor() {
    super();
  }

  ngOnChanges()
  {
    super.ngOnChanges();
    this.formControl.setValue(this.getData()?.data?.selected);
  }

  getData(): TypeModels.Select
  {
    let widget = this.formData?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'Select')
    {
      return {
        type: 'Select',
        data: {type: 'Select.Data', options: {}, selected: ''},
        placeholder: '',
        validators: [],
        label: 'undefined label',
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return <TypeModels.Select> this.formData.widgets[this.widgetId];
  }


  getOptionIds(): string[]
  {
    return Object.keys(this.getData().data?.options);
  }

  getOptionValue(id: string): string | null
  {
    return this.getData().data?.options[id];
  }
}

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

import {Component, Input, OnInit} from '@angular/core';
import Form = Ngface.Form;
import Button = Ngface.Button;
import {Ngface} from '../ngface-models';

@Component({
  selector: 'ngface-button',
  templateUrl: './ngface-button.component.html',
  styleUrls: ['./ngface-button.component.scss']
})
export class NgfaceButtonComponent implements OnInit
{

  @Input()
  formData: Form;

  @Input()
  widgetId: string;

  @Input()
  enabled = true;

  @Input()
  badge?: string;

  constructor()
  {
  }

  ngOnInit(): void
  {
  }

  getData(): Button
  {
    let widget = this.formData?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'Button')
    {
      return {
        data: {type: 'VoidWidgetData'},
        style: 'NONE',
        type: '',
        label: 'undefined button',
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return <Button> this.formData.widgets[this.widgetId];
  }

  onClick()
  {
    console.log('Button clicked');
  }

  getClass()
  {
    return {
      '': this.getData().style === 'NONE',
      'mat-primary': this.getData().style === 'PRIMARY',
      'mat-accent': this.getData().style === 'ACCENT',
      'mat-warn': this.getData().style === 'WARN'
    };
  }
}

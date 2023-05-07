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

import {Component, ViewChild} from '@angular/core';
import {UntypedFormGroup} from '@angular/forms';
import {Ngface} from './ngface-models';
import {NgfaceFormComponent} from './ngface-form/ngface-form.component';

@Component({
  selector: 'ngface-form-base',
  template: ''
})
export abstract class FormBaseComponent
{
  @ViewChild(NgfaceFormComponent) formComponent!: NgfaceFormComponent;

  private _formData?: Ngface.Form;
  get formData(): Ngface.Form | undefined
  {
    return this._formData;
  }
  set formData(formData: Ngface.Form | undefined)
  {
    this._formData = formData;
  }

  constructor()
  {
  }

  get formGroup(): UntypedFormGroup
  {
    return this.formComponent.formGroup;
  }

  public isWidgetAvailable(widgetId: string): boolean
  {
    return !!this.formData?.widgets[widgetId];
  }

  getSubmitData(): { [key: string]: Ngface.WidgetData }
  {
    return this.formComponent.getSubmitData();
  }
}

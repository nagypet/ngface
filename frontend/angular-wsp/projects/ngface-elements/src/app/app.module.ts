/*
 * Copyright 2020-2024 the original author or authors.
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

import {Injector, LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {NgfaceButtonComponent} from '../../../ngface/src/lib/widgets/ngface-button/ngface-button.component';
import {createCustomElement} from '@angular/elements';
import {NgfaceModule} from '../../../ngface/src/lib/ngface.module';
import {NgfaceDataTableComponent} from '../../../ngface/src/lib/widgets/ngface-data-table/ngface-data-table.component';
import {NgfaceDateInputComponent} from '../../../ngface/src/lib/widgets/ngface-date-input/ngface-date-input.component';
import {NgfaceDateRangeInputComponent} from '../../../ngface/src/lib/widgets/ngface-date-range-input/ngface-date-range-input.component';
import {NgfaceTextInputComponent} from '../../../ngface/src/lib/widgets/ngface-text-input/ngface-text-input.component';
import {NgfaceNumericInputComponent} from '../../../ngface/src/lib/widgets/ngface-numeric-input/ngface-numeric-input.component';
import {NgfaceSelectComponent} from '../../../ngface/src/lib/widgets/ngface-select/ngface-select.component';
import {AppComponent} from './app.component';
import {NgfaceFormComponent} from '../../../ngface/src/lib/form/ngface-form/ngface-form.component';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from '@angular/material/form-field';
import {MAT_DATE_LOCALE} from '@angular/material/core';
import {registerLocaleData} from '@angular/common';

import localeDe from '@angular/common/locales/de';
import localeDeExtra from '@angular/common/locales/extra/de';
registerLocaleData(localeDe, 'de-DE', localeDeExtra);

@NgModule({
    imports: [
        BrowserModule,
        NgfaceModule,
        AppComponent
    ],
    providers: [
        { provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: { appearance: 'fill' } },
        { provide: MAT_DATE_LOCALE, useValue: 'hu' },
        { provide: LOCALE_ID, useValue: 'de-DE' }
    ]
})
export class AppModule
{
  constructor(private injector: Injector)
  {
  }

  ngDoBootstrap()
  {
    const buttonElement = createCustomElement(NgfaceButtonComponent, {injector: this.injector});
    customElements.define('ngface-button-element', buttonElement);

    const dataTableElement = createCustomElement(NgfaceDataTableComponent, {injector: this.injector});
    customElements.define('ngface-data-table-element', dataTableElement);

    const dateInputElement = createCustomElement(NgfaceDateInputComponent, {injector: this.injector});
    customElements.define('ngface-date-input-element', dateInputElement);

    const dateRangeInputElement = createCustomElement(NgfaceDateRangeInputComponent, {injector: this.injector});
    customElements.define('ngface-date-range-input-element', dateRangeInputElement);

    const textInputElement = createCustomElement(NgfaceTextInputComponent, {injector: this.injector});
    customElements.define('ngface-text-input-element', textInputElement);

    const numericInputElement = createCustomElement(NgfaceNumericInputComponent, {injector: this.injector});
    customElements.define('ngface-numeric-input-element', numericInputElement);

    const selectElement = createCustomElement(NgfaceSelectComponent, {injector: this.injector});
    customElements.define('ngface-select-element', selectElement);

    const formElement = createCustomElement(NgfaceFormComponent, {injector: this.injector});
    customElements.define('ngface-form-element', formElement);
  }
}

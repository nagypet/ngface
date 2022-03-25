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

import {Component, Inject, LOCALE_ID} from '@angular/core';
import {TypeModels} from '../../dto-models';
import {InputBaseComponent} from '../input-base.component';
import {formatNumber, getLocaleNumberSymbol, NumberSymbol} from '@angular/common';
import NumericInput = TypeModels.NumericInput;

@Component({
  selector: 'ngface-numeric-input',
  templateUrl: './numeric-input.component.html',
  styleUrls: ['./numeric-input.component.scss']
})
export class NumericInputComponent extends InputBaseComponent
{
  constructor(@Inject(LOCALE_ID) public locale: string)
  {
    super();
  }

  /**
   * Every time the model changes, we will parse in the intl format numeric value and set it back to the formControl, so that the formControl
   * always hold an ISO numeric
   */
  onChange()
  {
    console.log('onChange original value:' + this.formControl.value);
    let parsedValue = this.parseIntlValue();
    console.log('onChange:' + parsedValue.toString());
    this.formControl.setValue(parsedValue.toString(), {emitModelToViewChange: false});
  }


  /**
   * Formatting model value to intl format and writing into the HTMLElement
   *
   * @param target
   */
  onBlur(target: EventTarget | null)
  {
    if (!target)
    {
      return;
    }

    let parsedValue = this.formControl.value;
    let formattedValue = this.getFormattedValueAsText(parsedValue);

    console.log('intl value: ' + formattedValue);
    (<HTMLInputElement> target).value = formattedValue;
    console.log('model value: ' + this.formControl.value);
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
    if (isNaN(value))
    {
      return '';
    }
    return formatNumber(value, this.locale, this.getDigitsInfo());
  }

  private parseIntlValue(): number
  {
    let localeSpecificValue: string = this.formControl.value;
    console.log('localeSpecificValue: ' + localeSpecificValue);

    // Now remove digit grouping symbol and replace locale specific comma with dot
    let digitGroupingSymbol = getLocaleNumberSymbol(this.locale, NumberSymbol.Group);
    let decimalSymbol = getLocaleNumberSymbol(this.locale, NumberSymbol.Decimal);
    console.log(`digitGroupingSymbol: ${digitGroupingSymbol}, decimalSymbol: ${decimalSymbol}`);
    let valueString = this.replaceAll(localeSpecificValue, digitGroupingSymbol, '').replace(decimalSymbol, '.');
    console.log('valueString: ' + valueString);

    let parsedValue = parseFloat(valueString);
    console.log('parsedValue: ' + parsedValue);
    return parsedValue;
  }

  getDigitsInfo(): string
  {
    // '0.2-2'
    let precision = this.getData()?.precision;
    return `0.${precision}-${precision}`;
  }

  escapeRegExp(string: string)
  {
    return string.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'); // $& means the whole matched string
  }

  replaceAll(str: string, find: string, replace: string)
  {
    return str.replace(new RegExp(this.escapeRegExp(find), 'g'), replace);
  }
}

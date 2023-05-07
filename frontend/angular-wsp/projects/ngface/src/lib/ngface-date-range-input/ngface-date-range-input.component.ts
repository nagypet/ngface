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
import {Ngface} from '../ngface-models';
import {UntypedFormControl, UntypedFormGroup, ValidatorFn} from '@angular/forms';

@Component({
  selector: 'ngface-date-range-input',
  templateUrl: './ngface-date-range-input.component.html',
  styleUrls: ['./ngface-date-range-input.component.scss']
})
export class NgfaceDateRangeInputComponent extends InputBaseComponent implements OnChanges
{

  range = new UntypedFormGroup({
    start: new UntypedFormControl(),
    end: new UntypedFormControl(),
  });

  constructor()
  {
    super();
  }

  ngOnChanges(): void
  {
    let startDate = this.getData()?.data?.startDate ? this.getData()?.data?.startDate : '';
    let endDate = this.getData()?.data?.endDate ? this.getData()?.data?.endDate : '';
    this.range.setValue({start: startDate, end: endDate});

    // Validators for startDate
    let startDateValidators = new Array<ValidatorFn>();
    this.getData()?.validators?.forEach(v =>
    {
      this.createNgValidators(v).forEach(ngValidator => startDateValidators.push(ngValidator));
    });
    this.range.controls['start']?.setValidators(startDateValidators);

    // Validators for endDate
    let endDateValidators = new Array<ValidatorFn>();
    this.getData()?.validators?.forEach(v =>
    {
      this.createNgValidators(v).forEach(ngValidator => endDateValidators.push(ngValidator));
    });
    this.range.controls['end']?.setValidators(endDateValidators);

    this.getData().enabled ? this.range.enable() : this.range.disable();

    // Adding FormControl to the FormGroup
    this.formgroup?.addControl(this.widgetid, this.range);
  }


  getData(): Ngface.DateRangeInput
  {
    let widget = this.formdata?.widgets[this.widgetid];
    if (!widget || widget?.type !== 'DateRangeInput')
    {
      return {
        type: 'DateRangeInput',
        data: {type: 'DateRangeInput.Data', startDate: new Date(), endDate: new Date()},
        placeholder: '',
        validators: [],
        placeholder2: '',
        validators2: [],
        label: 'undefined label',
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return this.formdata?.widgets[this.widgetid] as Ngface.DateRangeInput;
  }


  getValidationErrors(): string
  {
    const validationErrorsStart = this.getValidationErrorsFromFormControl(this.range.controls['start'], this.getData()?.validators);
    const validationErrorsEnd = this.getValidationErrorsFromFormControl(this.range.controls['end'], this.getData()?.validators2);

    const validationErrors = validationErrorsStart.concat(validationErrorsEnd);
    return validationErrors?.join(' ');
  }
}

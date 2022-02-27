import {Component, OnChanges} from '@angular/core';
import {InputBaseComponent} from '../input-base.component';
import {TypeModels} from '../../dto-models';
import DateRangeInput = TypeModels.DateRangeInput;
import {FormControl, FormGroup, ValidatorFn} from '@angular/forms';

@Component({
  selector: 'ngface-date-range-input',
  templateUrl: './date-range-input.component.html',
  styleUrls: ['./date-range-input.component.scss']
})
export class DateRangeInputComponent extends InputBaseComponent implements OnChanges
{

  range = new FormGroup({
    start: new FormControl(),
    end: new FormControl(),
  });

  constructor()
  {
    super();
  }

  ngOnChanges(): void
  {
    let startDate = this.getData()?.startDate?.value ? this.getData()?.startDate?.value : '';
    let endDate = this.getData()?.endDate?.value ? this.getData()?.endDate?.value : '';
    this.range.setValue({start: startDate, end: endDate});

    // Validators for startDate
    let startDateValidators = new Array<ValidatorFn>();
    this.getData().startDate?.validators?.forEach(v =>
    {
      this.createNgValidators(v).forEach(ngValidator => startDateValidators.push(ngValidator));
    });
    this.range.get('start')?.setValidators(startDateValidators);

    // Validators for endDate
    let endDateValidators = new Array<ValidatorFn>();
    this.getData().endDate?.validators?.forEach(v =>
    {
      this.createNgValidators(v).forEach(ngValidator => endDateValidators.push(ngValidator));
    });
    this.range.get('end')?.setValidators(endDateValidators);

    this.getData().enabled ? this.range.enable() : this.range.disable();

    this.formGroup.addControl(this.widgetId, this.range);
  }


  getData(): DateRangeInput
  {
    let widget = this.formData?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'DateRangeInput')
    {
      return {
        placeholder: '',
        validators: [],
        value: undefined,
        type: 'DateRangeInput',
        startDate: {
          value: new Date(),
          placeholder: '',
          validators: []
        },
        endDate: {
          value: new Date(),
          placeholder: '',
          validators: []
        },
        label: 'undefined label',
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return <DateRangeInput> this.formData.widgets[this.widgetId];
  }


  getValidationErrors(): string
  {
    var validationErrorsStart = this.getValidationErrorsFromFormControl(this.range.get('start'), this.getData()?.startDate?.validators);
    var validationErrorsEnd = this.getValidationErrorsFromFormControl(this.range.get('end'), this.getData()?.endDate?.validators);

    var validationErrors = validationErrorsStart.concat(validationErrorsEnd);
    return validationErrors?.join(' ');
  }
}

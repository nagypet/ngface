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
    //super.ngOnChanges();

    this.range.setValue({start: this.getData().value, end: this.getData().endDate});

    let validators = new Array<ValidatorFn>();
    this.getData().validators?.forEach(v =>
    {
      this.createNgValidators(v).forEach(ngValidator => validators.push(ngValidator));
    });
    this.formGroup.setValidators(validators);
    this.getData().enabled ? this.formGroup.enable() : this.formGroup.disable();

    this.formGroup.addControl(this.widgetId, this.range);
  }


  getData(): DateRangeInput
  {
    let widget = this.formData?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'DateRangeInput')
    {
      return {
        type: 'DateRangeInput',
        value: new Date(),
        endDate: new Date(),
        placeholder: 'widget id: ' + this.widgetId,
        label: 'undefined label',
        validators: [],
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return <DateRangeInput> this.formData.widgets[this.widgetId];
  }


  getValidationErrors()
  {
    var validationErrorsStart = this.getValidationErrorsFromFormControl(this.range.get('start'));
    var validationErrorsEnd = this.getValidationErrorsFromFormControl(this.range.get('end'));

    return validationErrorsStart.concat(validationErrorsEnd);
  }
}

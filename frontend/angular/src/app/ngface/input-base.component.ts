import {AbstractControl, FormControl, FormGroup, FormGroupDirective, NgForm, ValidatorFn, Validators} from '@angular/forms';
import {TypeModels} from '../dto-models';
import {Component, Input, OnChanges} from '@angular/core';
import {ErrorStateMatcher} from '@angular/material/core';
import Form = TypeModels.Form;

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher
{
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean
  {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}


@Component({
  selector: 'ngface-input-base',
  template: ''
})
export abstract class InputBaseComponent implements OnChanges
{
  @Input()
  formGroup: FormGroup;

  @Input()
  formData: Form;

  @Input()
  widgetId: string;

  floatLabelControl = new FormControl('auto');

  formControl = new FormControl('', []);

  errorStateMatcher = new MyErrorStateMatcher();

  constructor()
  {
  }


  ngOnChanges(): void
  {
    // Setting the value
    this.formControl.setValue(this.getData()?.data?.value);

    // Validators
    let validators = new Array<ValidatorFn>();
    this.getData().validators?.forEach(v =>
    {
      this.createNgValidators(v).forEach(ngValidator => validators.push(ngValidator));
    });
    this.formControl.setValidators(validators);

    // Enabled status
    this.getData().enabled ? this.formControl.enable() : this.formControl.disable();

    // Adding FormControl to the FormGroup
    this.formGroup.addControl(this.widgetId, this.formControl);
  }


  protected createNgValidators(validator: TypeModels.Validator<any>): ValidatorFn[]
  {
    let validators = new Array<ValidatorFn>();

    switch (validator.type)
    {
      case 'Required':
        validators.push(Validators.required);
        break;

      case 'Min':
        validators.push(Validators.min((<TypeModels.Min> validator).min));
        break;

      case 'Max':
        validators.push(Validators.max((<TypeModels.Max> validator).max));
        break;

      case 'Size':
        validators.push(Validators.minLength((<TypeModels.Size> validator).min));
        validators.push(Validators.maxLength((<TypeModels.Size> validator).max));
        break;

      case 'Email':
        validators.push(Validators.email);
        break;

      case 'Pattern':
        validators.push(Validators.pattern((<TypeModels.Pattern> validator).pattern));
        break;

      default:
        console.error('Unknown validator type: ' + validator.type);
    }

    return validators;
  }


  abstract getData(): TypeModels.Input<any, any, any>


  /**
   * Returns a validator by name
   * @param name
   * @private
   */
  protected getValidator(name: string): TypeModels.Validator<any> | undefined
  {
    let validatorName = name;
    if (name === 'minlength' || name === 'maxlength')
    {
      validatorName = 'Size';
    }
    return this.getData()?.validators?.find(c => c.type.toLowerCase() === validatorName.toLowerCase());
  }


  /**
   * Returns a validator by name
   * @param name
   * @private
   */
  protected getValidatorFrom(validators: TypeModels.Validator<any>[] | undefined, name: string): TypeModels.Validator<any> | undefined
  {
    let validatorName = name;
    if (name === 'minlength' || name === 'maxlength')
    {
      validatorName = 'Size';
    }
    return validators?.find(c => c.type.toLowerCase() === validatorName.toLowerCase());
  }


  /**
   * Returns true if validators contain "Required"
   */
  isRequired(): boolean
  {
    return !!this.getValidator('Required');
  }


  getMinLength(): number | null
  {
    var sizeValidator = this.getValidator('Size');
    if (sizeValidator)
    {
      return (<TypeModels.Size> sizeValidator).min;
    }
    return null;
  }


  getMaxLength(): number | null
  {
    var sizeValidator = this.getValidator('Size');
    if (sizeValidator)
    {
      return (<TypeModels.Size> sizeValidator).max;
    }
    return null;
  }


  getValidationErrors(): string
  {
    return this.getValidationErrorsFromFormControl(this.formControl, this.getData()?.validators).join(' ');
  }


  getValidationErrorsFromFormControl(fc: AbstractControl | null, validators: TypeModels.Validator<any>[] | undefined): string[]
  {
    let validationErrors = fc?.errors;
    let errorMessages = new Array<string>();
    if (validationErrors)
    {
      Object.keys(validationErrors).forEach(v =>
      {
        let validator = this.getValidatorFrom(validators, v);
        if (validator)
        {
          errorMessages.push(validator.message);
        }
      });
    }
    return errorMessages
  }
}

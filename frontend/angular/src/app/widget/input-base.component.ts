import {FormControl, ValidatorFn, Validators} from '@angular/forms';
import {TypeModels} from '../dto-models';
import {Component, Input, OnChanges} from '@angular/core';
import Form = TypeModels.Form;

@Component({
  selector: 'ngface-input-base',
  template: ''
})
export abstract class InputBaseComponent implements OnChanges
{
  @Input()
  form: Form;

  @Input()
  widgetId: string;

  floatLabelControl = new FormControl('auto');

  formControl = new FormControl('', []);

  constructor()
  {
  }


  ngOnChanges(): void
  {
    let validators = new Array<ValidatorFn>();
    this.getData().validators?.forEach(v =>
    {
      this.createNgValidators(v).forEach(ngValidator => validators.push(ngValidator));
    });
    this.formControl.setValidators(validators);
  }


  private createNgValidators(validator: TypeModels.Validator<any>): ValidatorFn[]
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

      case 'Pattern':
        validators.push(Validators.pattern((<TypeModels.Pattern> validator).pattern));
        break;

      default:
        console.error('Unknown validator type: ' + validator.type);
    }

    return validators;
  }


  abstract getData(): TypeModels.Input<any, any>


  /**
   * Returns a validator by name
   * @param name
   * @private
   */
  private getValidator(name: string): TypeModels.Validator<any> | undefined
  {
    let validatorName = name;
    if (name === 'minlength' || name === 'maxlength')
    {
      validatorName = 'Size';
    }
    return this.getData().validators?.find(c => c.type.toLowerCase() === validatorName.toLowerCase());
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
      return (<TypeModels.Size>sizeValidator).min;
    }
    return null;
  }


  getMaxLength(): number | null
  {
    var sizeValidator = this.getValidator('Size');
    if (sizeValidator)
    {
      return (<TypeModels.Size>sizeValidator).max;
    }
    return null;
  }


  getValidationErrors()
  {
    let validationErrors = this.formControl.errors;
    let errorMessages = new Array<string>();
    if (validationErrors != null)
    {
      Object.keys(validationErrors).forEach(v =>
      {
        let validator = this.getValidator(v);
        if (validator)
        {
          errorMessages.push(validator.message);
        }
      });
    }
    return errorMessages.toString();
  }
}

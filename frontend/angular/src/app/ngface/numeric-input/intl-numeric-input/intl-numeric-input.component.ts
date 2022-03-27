import {Component, Inject, Input, LOCALE_ID} from '@angular/core';
import {ControlValueAccessor, FormControl, FormGroupDirective, NG_VALUE_ACCESSOR, NgForm} from '@angular/forms';
import {NumericFormatter} from '../../numeric-formatter';
import {getLocaleNumberSymbol, NumberSymbol} from '@angular/common';
import {ErrorStateMatcher} from '@angular/material/core';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher
{
  private component: IntlNumericInputComponent;

  constructor(component: IntlNumericInputComponent)
  {
    this.component = component;
  }

  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean
  {
    return this.component.validationErrors !== null;
  }
}

@Component({
  selector: 'ngface-intl-numeric-input',
  templateUrl: './intl-numeric-input.component.html',
  styleUrls: ['./intl-numeric-input.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: IntlNumericInputComponent
    }
  ]
})
export class IntlNumericInputComponent implements ControlValueAccessor
{

  @Input()
  label: string;

  @Input()
  placeholder: string;

  @Input()
  hint: string;

  @Input()
  required: boolean;

  _precision: number;
  @Input()
  set precision(precision: number)
  {
    this._precision = precision;
    this.valueText = NumericFormatter.getFormattedValueAsText(this.value, this.precision, this.locale);
  }

  get precision()
  {
    return this._precision;
  }

  @Input()
  prefix: string;

  @Input()
  suffix: string;

  @Input()
  validationErrors: string | null;

  valueText: string;
  value: number | null;

  floatLabelControl = new FormControl('auto');

  errorStateMatcher = new MyErrorStateMatcher(this);

  onChangeFn = (value: number | null) =>
  {
  };

  onTouchedFn = () =>
  {
  };

  touched = false;
  disabled = false;

  constructor(@Inject(LOCALE_ID) public locale: string)
  {
  }

  registerOnChange(onChange: any)
  {
    this.onChangeFn = onChange;
  }

  registerOnTouched(onTouched: any)
  {
    this.onTouchedFn = onTouched;
  }


  /**
   * Converting ISO => intl
   */
  writeValue(value: number | null)
  {
    console.log('writeValue: ' + value);
    this.valueText = NumericFormatter.getFormattedValueAsText(value, this.precision, this.locale);
    this.value = value;
  }

  markAsTouched()
  {
    if (!this.touched)
    {
      this.onTouchedFn();
      this.touched = true;
    }
  }

  setDisabledState(disabled: boolean)
  {
    this.disabled = disabled;
  }


  /**
   * Converting intl => ISO
   */
  onChange()
  {
    console.log('onChange valueText: ' + this.valueText);
    this.markAsTouched();
    if (!this.disabled)
    {
      let localeSpecificValue: string = this.valueText;
      console.log('localeSpecificValue: ' + localeSpecificValue);

      // Now remove digit grouping symbol and replace locale specific comma with dot
      let digitGroupingSymbol = getLocaleNumberSymbol(this.locale, NumberSymbol.Group);
      let decimalSymbol = getLocaleNumberSymbol(this.locale, NumberSymbol.Decimal);
      console.log(`digitGroupingSymbol: ${digitGroupingSymbol}, decimalSymbol: ${decimalSymbol}`);
      let valueString = this.replaceAll(localeSpecificValue, digitGroupingSymbol, '').replace(decimalSymbol, '.');
      console.log('valueString: ' + valueString);

      let parsedValue = parseFloat(valueString);
      console.log('parsedValue: ' + parsedValue);

      if (!isNaN(parsedValue))
      {
        this.value = parsedValue;
        this.onChangeFn(parsedValue);
      } else
      {
        this.value = null;
        this.onChangeFn(null);
      }
    }
  }

  escapeRegExp(string: string)
  {
    return string.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'); // $& means the whole matched string
  }

  replaceAll(str: string, find: string, replace: string)
  {
    return str.replace(new RegExp(this.escapeRegExp(find), 'g'), replace);
  }


  /**
   * Formatting model value to intl format
   *
   * @param target
   */
  onBlur()
  {
    if (!this.valueText)
    {
      return;
    }

    this.writeValue(this.value);
  }
}

import {Component, Input} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {TypeModels} from '../dto-models';
import Form = TypeModels.Form;
import WidgetData = TypeModels.WidgetData;

@Component({
  selector: 'ngface-form-base',
  template: ''
})
export abstract class FormBaseComponent
{
  formData: Form;
  formGroup = new FormGroup({});

  constructor()
  {
  }

  getSubmitData(): {[key: string]: WidgetData}
  {
    let submitData: {[key: string]: WidgetData} = {};
    Object.keys(this.formGroup.controls).forEach(controlName =>
    {
      let widget = this.formData.widgets[controlName];
      let widgetType: string = widget?.type;
      switch (widgetType)
      {
        case 'TextInput':
        case 'NumericInput':
        case 'DateInput':
        case 'DateTimeInput':
          submitData[controlName] = <TypeModels.Value<any>>{type: widgetType + ".Data", value: this.formGroup.controls[controlName]?.value};
          break;

        case 'DateRangeInput':
          submitData[controlName] = <TypeModels.DateRangeInput.Data>{type: widgetType + ".Data", startDate: this.formGroup.controls[controlName]?.value?.start, endDate: this.formGroup.controls[controlName]?.value?.end};
          break;

        case 'Select':
          let selected = this.formGroup.controls[controlName]?.value;
          let selectedOption: { [index: string]: string } = {};
          selectedOption[selected] = widget?.data.options[selected];
          submitData[controlName] = <TypeModels.Select.Data>{type: widgetType + ".Data", options: selectedOption, selected: selected};
          break;
      }
    });

    return submitData;
  }
}

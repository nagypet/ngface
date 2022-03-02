import {Component, OnInit} from '@angular/core';
import {DemoService} from '../services/demo.service';
import {FormBaseComponent} from '../ngface/form-base.component';
import {TypeModels} from '../dto-models';
import WidgetData = TypeModels.WidgetData;
import TextInput = TypeModels.TextInput;
import {any} from 'codelyzer/util/function';

@Component({
  selector: 'app-demo-form1',
  templateUrl: './demo-form1.component.html',
  styleUrls: ['./demo-form1.component.scss']
})
export class DemoForm1Component extends FormBaseComponent implements OnInit
{

  constructor(private demoService: DemoService)
  {
    super();
  }

  ngOnInit()
  {
    this.demoService.getDemoForm().subscribe(data =>
    {
      console.log(data);
      this.formData = data;
    });
  }


  onOk()
  {
    this.formGroup.markAllAsTouched();
    if (!this.formGroup.valid)
    {
      console.warn('Data is invalid!');
    } else
    {
      let submitData: {[key: string]: WidgetData} = {};
      Object.keys(this.formGroup.controls).forEach(controlName =>
      {
        var widgetType: string = this.formData.widgets[controlName]?.type;
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
        }
      });

      console.log(submitData);

      this.demoService.submitDemoForm({widgetDataMap: submitData}).subscribe(
        () => console.log('sumbitted'),
        error => console.log(error));
    }
  }
}

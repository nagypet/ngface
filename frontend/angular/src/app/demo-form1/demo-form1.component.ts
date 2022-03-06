import {Component, OnInit} from '@angular/core';
import {DemoService} from '../services/demo.service';
import {FormBaseComponent} from '../ngface/form-base.component';
import {TypeModels} from '../dto-models';
import {TableReloadEvent} from '../ngface/data-table/data-table.component';
import WidgetData = TypeModels.WidgetData;

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

      console.log(submitData);

      this.demoService.submitDemoForm({widgetDataMap: submitData}).subscribe(
        () => console.log('sumbitted'),
        error => console.log(error));
    }
  }


  onTableReload($event: TableReloadEvent)
  {
    this.demoService.getDemoForm($event.pageIndex, $event.pageSize, $event.sortColumn, $event.sortDirection).subscribe(data =>
    {
      console.log(data);
      this.formData = data;
    });
  }
}

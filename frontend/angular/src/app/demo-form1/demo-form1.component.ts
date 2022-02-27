import {Component, OnInit} from '@angular/core';
import {DemoService} from '../services/demo.service';
import {FormBaseComponent} from '../ngface/form-base.component';

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
      this.dumpSimpleControl('name');
      this.dumpSimpleControl('place-of-birth');
      this.dumpSimpleControl('email');
      this.dumpSimpleControl('role');
      this.dumpSimpleControl('amount');
      this.dumpSimpleControl('count-samples');
      this.dumpSimpleControl('check-in-date');
      this.dumpSimpleControl('check-out-date');
      this.dumpDateRangeControl('date-range');
      this.dumpDateRangeControl('date-range2');
    }
  }

  private dumpSimpleControl(widgetId: string)
  {
    let submitValue = this.formGroup.get(widgetId)?.value;
    console.log(widgetId + ': ' + submitValue?.toString());
  }

  private dumpDateRangeControl(widgetId: string)
  {
    let fg = this.formGroup.get(widgetId);
    console.log(widgetId + ': start: ' + fg?.get('start')?.value + ' end: ' + fg?.get('end')?.value);
  }

}

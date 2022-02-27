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
      this.log('name');
      this.log('place-of-birth');
      this.log('email');
      this.log('role');
      this.log('amount');
      this.log('count-samples');
      this.log('check-in-date');
      this.log('check-out-date');
      this.log('date-range');
      this.log('date-range-end');
    }
  }

  private log(widgetId: string)
  {
    console.log(widgetId + ': ' + this.formGroup.get(widgetId)?.value);
  }

}

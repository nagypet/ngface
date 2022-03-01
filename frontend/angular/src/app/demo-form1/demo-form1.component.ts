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
      let submit = new Map();
      Object.keys(this.formGroup.controls).forEach(controlName =>
      {
        var dataType = this.formData.widgets[controlName]?.data?.type;
        submit.set(controlName, {type: dataType, value: this.formGroup.controls[controlName]?.value});
      });
      console.log(submit);
    }
  }
}

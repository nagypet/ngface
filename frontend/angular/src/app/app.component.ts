import {Component, OnInit} from '@angular/core';
import {DemoService} from './services/demo.service';
import {TypeModels} from './dto-models';
import Form = TypeModels.Form;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit
{
  title = 'angular';
  form: Form;

  constructor(private demoService: DemoService)
  {
  }

  ngOnInit()
  {
    this.demoService.getDemoForm().subscribe(data =>
    {
      console.log(data);
      this.form = data;
    });
  }

  public getData(id: string)
  {
    return this.form?.widgets[id];
  }

}

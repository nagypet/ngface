import {Component, OnInit} from '@angular/core';
import {DemoService} from './services/demo.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'angular';

  constructor(private demoService: DemoService) {}

  ngOnInit() {
    this.demoService.getDemoForm().subscribe(data => {
      console.log(data);
    });
  }

}

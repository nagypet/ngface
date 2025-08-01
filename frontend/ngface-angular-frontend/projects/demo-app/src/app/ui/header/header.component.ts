/*
 * Copyright 2020-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormBaseComponent} from '../../../../../ngface/src/lib/form/form-base.component';
import {NgfaceTitlebarComponent} from '../../../../../ngface/src/lib/titlebar/ngface-titlebar/ngface-titlebar.component';
import {Ngface} from '../../../../../ngface/src/lib/ngface-models';
import {TitlebarService} from '../../core/services/titlebar.service';
import {DeviceTypeService} from '../../../../../ngface/src/lib/services/device-type.service';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  imports: [
    NgfaceTitlebarComponent
  ],
  standalone: true
})
export class HeaderComponent extends FormBaseComponent implements OnInit
{

  constructor(
    private router: Router,
    private titlebarService: TitlebarService,
    public deviceTypeService: DeviceTypeService
  )
  {
    super();
  }


  ngOnInit(): void
  {
    this.titlebarService.getTitlebar().subscribe(form =>
    {
      console.log(form);
      this.formData = form;
    });
  }


  onTitlebarMenuItemClick($event: Ngface.Menu.Item): void
  {
    switch ($event.id)
    {
      case 'widgets_demo':
        this.router.navigate(['widget-demo']);
        break;

      case 'table_demo':
        this.router.navigate(['table-demo']);
        break;
    }
  }


  onTitlebarActionClick($event: Ngface.Action): void
  {

  }


  getLogoUrl(): string
  {
    return `themes/${environment.theme}/company_logo.png`;
  }


  getSelectedMenuItemId(): string
  {
    const url = this.router.url.split('/')[1];
    switch (url)
    {
      case 'widget-demo':
        return 'widgets_demo';

      case 'table-demo':
        return 'table_demo';
    }

    return url;
  }
}

/*
 * Copyright 2020-2023 the original author or authors.
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

import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {Ngface} from '../../ngface-models';
import {NgClass, NgFor} from '@angular/common';
import {MatMenuModule} from '@angular/material/menu';
import {MatBadgeModule} from '@angular/material/badge';
import {DeviceTypeService} from '../../services/device-type.service';
import {ResponsiveClassDirective} from '../../directives/responsive-class-directive';

@Component({
  selector: 'ngface-titlebar',
  templateUrl: './ngface-titlebar.component.html',
  styleUrls: ['./ngface-titlebar.component.scss'],
  imports: [
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    NgFor,
    MatMenuModule,
    MatBadgeModule,
    ResponsiveClassDirective,
    NgClass
  ],
  standalone: true
})
export class NgfaceTitlebarComponent implements OnChanges
{
  @Input()
  formdata?: Ngface.Form;

  @Input()
  widgetid = '';

  @Output()
  menuItemClick: EventEmitter<Ngface.Menu.Item> = new EventEmitter();

  @Output()
  actionClick: EventEmitter<Ngface.Action> = new EventEmitter();

  private selectedMenuItemId?: string;

  constructor(public deviceTypeService: DeviceTypeService)
  {
  }

  ngOnChanges(changes: SimpleChanges): void
  {
    this.selectedMenuItemId = this.getData().menu.defaultItemId;
    const item = this.getData().menu.items.find(i => i.id === this.selectedMenuItemId);
    if (item)
    {
      this.onMenuClick(item);
    }
  }

  getData(): Ngface.Titlebar
  {
    const widget = this.formdata?.widgets[this.widgetid];
    if (!widget || widget?.type !== 'Titlebar')
    {
      return {
        type: 'Titlebar',
        appTitle: 'App title',
        version: '',
        menu: {items: [], defaultItemId: ''},
        data: {type: 'VoidWidgetData'},
        actions: [],
        label: 'undefined label',
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return this.formdata?.widgets[this.widgetid] as Ngface.Titlebar;
  }

  onActionClick(action: Ngface.Action): void
  {
    this.actionClick.emit(action);
  }

  onMenuClick(menuItem: Ngface.Menu.Item): void
  {
    this.selectedMenuItemId = menuItem.id;
    this.menuItemClick.emit(menuItem);
  }

  getClass(menuItem: Ngface.Menu.Item): string
  {
    if (menuItem.id === this.selectedMenuItemId)
    {
      return 'selected';
    }

    return '';
  }

  getIcon(menuItem: Ngface.Menu.Item): string
  {
    if (menuItem.id === this.selectedMenuItemId)
    {
      return 'check';
    }

    return menuItem.icon;
  }
}

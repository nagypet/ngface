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

import {Component, EventEmitter, Input, Output} from '@angular/core';
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
export class NgfaceTitlebarComponent
{
    @Input()
    formdata?: Ngface.Form;

    @Input()
    widgetid = '';

    @Output()
    menuItemClick: EventEmitter<Ngface.Menu.Item> = new EventEmitter();

    @Output()
    actionClick: EventEmitter<Ngface.Action> = new EventEmitter();

    private selectedMenuItem?: Ngface.Menu.Item;

    constructor(public deviceTypeService: DeviceTypeService)
    {
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
                menu: {items: []},
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
        this.selectedMenuItem = menuItem;
        this.menuItemClick.emit(menuItem);
    }

    getClass(menuItem: Ngface.Menu.Item): string
    {
        if (menuItem.id === this.selectedMenuItem?.id)
        {
            return 'selected';
        }

        return '';
    }

    getIcon(menuItem: Ngface.Menu.Item): string
    {
        if (menuItem.id === this.selectedMenuItem?.id)
        {
            return 'check';
        }

        return menuItem.icon;
    }
}

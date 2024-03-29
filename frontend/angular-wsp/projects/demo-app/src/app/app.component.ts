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

import {Component, HostListener, OnInit} from '@angular/core';
import {HeaderComponent} from './ui/header/header.component';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {FooterComponent} from './ui/footer/footer.component';
import {DeviceTypeService} from '../../../ngface/src/lib/services/device-type.service';
import {NgIf} from '@angular/common';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
    standalone: true,
    imports: [HeaderComponent, RouterLink, RouterLinkActive, RouterOutlet, FooterComponent, NgIf]
})
export class AppComponent implements OnInit
{
    title = 'demo-app';


    constructor(public deviceTypeService: DeviceTypeService)
    {
    }

    ngOnInit(): void
    {
        this.onWindowResize();
    }


    @HostListener('window:resize', ['$event'])
    onWindowResize(): void
    {
        this.deviceTypeService.calculateDeviceType();
    }
}


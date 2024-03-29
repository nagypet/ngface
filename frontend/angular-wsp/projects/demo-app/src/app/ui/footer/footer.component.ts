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

import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ResponsiveClassDirective} from '../../../../../ngface/src/lib/directives/responsive-class-directive';
import {DeviceTypeService} from '../../../../../ngface/src/lib/services/device-type.service';

@Component({
    selector: 'app-footer',
    standalone: true,
    imports: [CommonModule, ResponsiveClassDirective],
    templateUrl: './footer.component.html',
    styleUrls: ['./footer.component.scss']
})
export class FooterComponent
{
    constructor(public deviceTypeService: DeviceTypeService)
    {
    }

    public getResolution(): string
    {
        return `${this.deviceTypeService.deviceType} - ${this.deviceTypeService.width} x ${this.deviceTypeService.height}`;
    }
}

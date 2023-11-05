/*
 * Copyright 2020-2022 the original author or authors.
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

import {Directive, HostBinding, Input} from '@angular/core';
import {DeviceTypeService} from '../services/device-type.service';

@Directive({
    selector: '[responsiveClass]',
    standalone: true
})
export class ResponsiveClassDirective
{
    // tslint:disable-next-line:variable-name
    protected _elementClass: string[] = [];

    @Input()
    responsiveClass!: string;

    @HostBinding('class')
    get elementClass(): string
    {
        return this.getDeviceDependentClass(this.responsiveClass);
    }

    set elementClass(val: string)
    {
        this._elementClass = val.split(' ');
    }

    constructor(public deviceTypeService: DeviceTypeService)
    {
    }

    private getDeviceDependentClass(input: string): string
    {
        const prefixes: string[] = input.split(' ');
        const deviceType = this.deviceTypeService.deviceType;
        const classNames: string[] = [];
        prefixes.forEach(prefix =>
        {
            classNames.push(`${prefix} ${prefix}-${deviceType}`);
            if (deviceType === 'Phone' || deviceType === 'Tablet')
            {
                classNames.push(`${prefix}-mobile`);
            }
        });
        const s = classNames.join(' ').toLowerCase();
        console.log(`input: ${input} => ${s}`);
        return s;
    }
}

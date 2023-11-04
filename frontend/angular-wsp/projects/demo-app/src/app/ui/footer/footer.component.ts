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

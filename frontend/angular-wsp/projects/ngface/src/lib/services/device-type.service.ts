import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

export enum DeviceTypes
{
    Phone = 'Phone',
    Tablet = 'Tablet',
    Desktop = 'Desktop'
}

@Injectable({
    providedIn: 'root'
})
export class DeviceTypeService
{
    public deviceTypeSubject: BehaviorSubject<DeviceTypes> = new BehaviorSubject<DeviceTypes>(DeviceTypes.Desktop);
    public width = 0;
    public height = 0;

    get deviceType(): DeviceTypes
    {
        return this.deviceTypeSubject.value;
    }


    constructor()
    {
    }

    public calculateDeviceType(): void
    {
        this.width = window.innerWidth;
        this.height = window.innerHeight;

        let type: DeviceTypes;
        if (this.width <= 700)
        {
            type = DeviceTypes.Phone;
        }
        else if (this.width <= 1024)
        {
            type = DeviceTypes.Tablet;
        }
        else
        {
            type = DeviceTypes.Desktop;
        }

        if (type !== this.deviceType)
        {
            this.deviceTypeSubject.next(type);
            console.log('resolution: ' + this.width + 'x' + this.height + ' device type: ' + this.deviceType.toString());
        }
    }
}

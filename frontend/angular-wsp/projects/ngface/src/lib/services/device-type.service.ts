import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

export enum DeviceTypes
{
    Phone = 'Phone',
    Tablet = 'Tablet',
    Desktop = 'Desktop'
}

export enum Orientation
{
    Portrait = 'Portrait',
    Landscape = 'Landscape'
}

@Injectable({
    providedIn: 'root'
})
export class DeviceTypeService
{
    public deviceTypeSubject: BehaviorSubject<DeviceTypes> = new BehaviorSubject<DeviceTypes>(DeviceTypes.Desktop);
    public orientationSubject: BehaviorSubject<Orientation> = new BehaviorSubject<Orientation>(Orientation.Landscape);
    public width = 0;
    public height = 0;
    get isHeightXSmall(): boolean
    {
        return this.height <= 400;
    }

    get deviceType(): DeviceTypes
    {
        return this.deviceTypeSubject.value;
    }

    get orientation(): Orientation
    {
        return this.orientationSubject.value;
    }


    constructor()
    {
    }

    public calculateDeviceType(): void
    {
        this.width = window.innerWidth;
        this.height = window.innerHeight;

        // Device Type
        let type: DeviceTypes;
        if (this.width <= 700 || this.height <= 700)
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

        // Orientation
        const o = this.width > this.height ? Orientation.Landscape : Orientation.Portrait;
        if (o !== this.orientation)
        {
            this.orientationSubject.next(o);
            console.log('orientation: ' + this.orientation.toString());
        }
    }
}

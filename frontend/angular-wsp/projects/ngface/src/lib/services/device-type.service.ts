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
    // tslint:disable-next-line:variable-name
    private _deviceTypeSubject: BehaviorSubject<DeviceTypes> = new BehaviorSubject<DeviceTypes>(DeviceTypes.Desktop);
    get deviceTypeSubject(): BehaviorSubject<DeviceTypes>
    {
        return this._deviceTypeSubject;
    }

    // tslint:disable-next-line:variable-name
    private _orientationSubject: BehaviorSubject<Orientation> = new BehaviorSubject<Orientation>(Orientation.Landscape);
    get orientationSubject(): BehaviorSubject<Orientation>
    {
        return this._orientationSubject;
    }

    // tslint:disable-next-line:variable-name
    private _width = 0;
    get width(): number
    {
        return this._width;
    }

    // tslint:disable-next-line:variable-name
    private _height = 0;
    get height(): number
    {
        return this._height;
    }

    get isHeightXSmall(): boolean
    {
        return this.height <= 450;
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
        this._width = window.innerWidth;
        this._height = window.innerHeight;

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
        const ori = this.width > this.height ? Orientation.Landscape : Orientation.Portrait;
        if (ori !== this.orientation)
        {
            this.orientationSubject.next(ori);
            console.log('orientation: ' + this.orientation.toString());
        }
    }
}

import {Injectable} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {BehaviorSubject} from 'rxjs';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {NgfaceErrorDialogComponent} from '../dialogs/ngface-error-dialog/ngface-error-dialog.component';
import {DeviceTypeService} from './device-type.service';


@Injectable({
    providedIn: 'root'
})
export class ErrorService
{
    private currentError: BehaviorSubject<HttpErrorResponse | undefined> = new BehaviorSubject<HttpErrorResponse | undefined>(undefined);
    private dialogRef?: MatDialogRef<any>;

    constructor(public dialog: MatDialog, private deviceTypeService: DeviceTypeService
    )
    {
    }


    handleError(error: HttpErrorResponse): void
    {
        this.currentError.next(error);
        if (!this.dialogRef)
        {
            this.dialogRef = this.dialog.open(NgfaceErrorDialogComponent, {
                minWidth: this.getErrorDialogWidth(),
                data: this.currentError,
                backdropClass: 'ngface-modal-dialog-backdrop'
            });
        }
    }


    private getErrorDialogWidth(): string
    {
        if (this.deviceTypeService.deviceType === 'Desktop')
        {
            return '800px';
        }

        if (this.deviceTypeService.deviceType === 'Tablet')
        {
            return Math.min(800, this.deviceTypeService.width * 0.8).toString() + 'px';
        }

        return this.deviceTypeService.width.toString() + 'px';
    }


    errorDialogClosing(): void
    {
        this.dialogRef = undefined;
    }
}

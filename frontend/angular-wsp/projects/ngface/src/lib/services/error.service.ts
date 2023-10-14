import {Injectable} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {BehaviorSubject} from 'rxjs';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {NgfaceErrorDialogComponent} from '../dialogs/ngface-error-dialog/ngface-error-dialog.component';


@Injectable({
    providedIn: 'root'
})
export class ErrorService
{
    private currentError: BehaviorSubject<HttpErrorResponse | undefined> = new BehaviorSubject<HttpErrorResponse | undefined>(undefined);
    private dialogRef?: MatDialogRef<any>;

    constructor(public dialog: MatDialog
    )
    {
    }


    handleError(error: HttpErrorResponse): void
    {
        this.currentError.next(error);
        if (!this.dialogRef)
        {
            this.dialogRef = this.dialog.open(NgfaceErrorDialogComponent, {
                width: '800px',
                data: this.currentError,
                backdropClass: 'ngface-modal-dialog-backdrop'
            });
        }
    }


    errorDialogClosing(): void
    {
        this.dialogRef = undefined;
    }
}

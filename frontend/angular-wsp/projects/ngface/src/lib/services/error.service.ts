import {Injectable} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {BehaviorSubject} from 'rxjs';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {NgfaceErrorDialogComponent} from '../ngface-error-dialog/ngface-error-dialog.component';

export interface ErrorObject
{
  httpError: HttpErrorResponse;
  timestamp: Date;
}


export interface ServerExceptionProperties {
    message: string;
    exceptionClass: string;
    superClasses: string[];
    stackTrace: StackTraceElement[];
    cause: ServerExceptionProperties;
}

export interface StackTraceElement {
    classLoaderName: string;
    moduleName: string;
    moduleVersion: string;
    methodName: string;
    fileName: string;
    lineNumber: number;
    className: string;
    nativeMethod: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class ErrorService
{
  private currentError: BehaviorSubject<ErrorObject | undefined> = new BehaviorSubject<ErrorObject | undefined>(undefined);
  private dialogRef?: MatDialogRef<any>;

  constructor(public dialog: MatDialog
  )
  {
  }


  handleError(error: HttpErrorResponse): void
  {
    this.currentError.next({httpError: error, timestamp: new Date()});
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

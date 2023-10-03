import {Component, Inject, OnInit} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {BehaviorSubject} from 'rxjs';
import {ErrorObject, ErrorService, ServerExceptionProperties} from '../services/error.service';

@Component({
  selector: 'lib-ngface-error-dialog',
  templateUrl: './ngface-error-dialog.component.html',
  styleUrls: ['./ngface-error-dialog.component.scss']
})
export class NgfaceErrorDialogComponent implements OnInit
{
  public showDetails = false;
  private data?: HttpErrorResponse;
  private timestamp?: Date;

  constructor(public dialogRef: MatDialogRef<NgfaceErrorDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public inputData: BehaviorSubject<ErrorObject | undefined>,
              private errorService: ErrorService
  )
  {
    inputData.subscribe(error =>
    {
      this.data = error?.httpError;
      this.timestamp = error?.timestamp;
    });
  }

  ngOnInit(): void
  {
    this.dialogRef.keydownEvents().subscribe(event =>
    {
      if (event.key === 'Escape')
      {
        this.onCancel();
      }
    });

    this.dialogRef.backdropClick().subscribe(event =>
    {
      this.onCancel();
    });
  }

  onOkClick(): void
  {
    this.errorService.errorDialogClosing();
    this.dialogRef.close();
  }

  private onCancel(): void
  {
    this.errorService.errorDialogClosing();
    this.dialogRef.close();
  }

  onDetailsClick(): void
  {
    this.showDetails = !this.showDetails;
  }

  public getErrorText(): string
  {
    if (this.data?.error?.exception?.exceptionClass === 'hu.perit.spvitamin.core.exception.ApplicationException' ||
      this.data?.error?.exception?.exceptionClass === 'hu.perit.spvitamin.core.exception.ApplicationRuntimeException')
    {
      return this.data?.error?.exception?.message;
    }

    return '';
  }

  public getErrorDetails(): string
  {
    let details = '';
    if (!this.data?.error.exception)
    {
      details += `${this.data?.error}<br><br>`;
    }
    details += `Status: ${this.data?.status} - ${this.data?.statusText}`;
    details += `<br>Timestamp: ${this.timestamp?.toLocaleString('hu')}`;
    details += `<br>Message: ${this.data?.message}`;
    details += `<br>URL: ${this.data?.url}`;
    details += `<br>`;
    if (this.data?.error.exception)
    {
      details += '<br>' + this.getExceptionAsText(this.data?.error.exception, 0);
    }
    return details;
  }

  private getExceptionAsText(e: ServerExceptionProperties, n: number): string
  {
    const s = `${e.exceptionClass}: ${e.message}`;

    if (!e.cause)
    {
      return s;
    }

    if (n >= 10)
    {
      return s + '<br>...';
    }

    return s + '<br>caused by: ' + this.getExceptionAsText(e.cause, ++n);
  }
}

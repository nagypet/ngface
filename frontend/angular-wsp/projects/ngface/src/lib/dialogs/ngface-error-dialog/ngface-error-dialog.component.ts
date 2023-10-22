import {Component, Inject, OnInit} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import {BehaviorSubject} from 'rxjs';
import {ErrorService} from '../../services/error.service';
import { A11yModule } from '@angular/cdk/a11y';
import { MatButtonModule } from '@angular/material/button';
import { NgScrollbarModule } from 'ngx-scrollbar';
import { NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

export interface SpvitaminErrorResponse
{
    timestamp?: Date;
    status?: number;
    error?: any;
    path: string | null | undefined;
    traceId?: string;
    exception?: ServerExceptionProperties;
    type?: string;
}

export interface ErrorResponse extends SpvitaminErrorResponse
{
    statusText?: string;
    message?: string;
}

export interface ServerExceptionProperties
{
    message: string;
    exceptionClass: string;
    superClasses: string[];
    stackTrace: StackTraceElement[];
    cause: ServerExceptionProperties;
}

export interface StackTraceElement
{
    classLoaderName: string;
    moduleName: string;
    moduleVersion: string;
    methodName: string;
    fileName: string;
    lineNumber: number;
    className: string;
    nativeMethod: boolean;
}

@Component({
    selector: 'lib-ngface-error-dialog',
    templateUrl: './ngface-error-dialog.component.html',
    styleUrls: ['./ngface-error-dialog.component.scss'],
    standalone: true,
    imports: [MatDialogModule, MatIconModule, NgIf, NgScrollbarModule, MatButtonModule, A11yModule]
})
export class NgfaceErrorDialogComponent implements OnInit
{
    public showDetails = false;
    private data: ErrorResponse = {timestamp: new Date(), status: undefined, path: undefined, traceId: undefined, exception: undefined, statusText: undefined, message: undefined};

    constructor(public dialogRef: MatDialogRef<NgfaceErrorDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public inputData: BehaviorSubject<HttpErrorResponse | undefined>,
                private errorService: ErrorService
    )
    {
        inputData.subscribe(error =>
        {
            if (error?.error.timestamp && error?.error.exception)
            {
                // This is probably an instance of SpvitaminErrorResponse
                const spvitaminError: SpvitaminErrorResponse = error?.error;
                this.data = {
                    timestamp: spvitaminError.timestamp,
                    status: spvitaminError.status,
                    error: spvitaminError.error,
                    path: spvitaminError.path,
                    traceId: spvitaminError.traceId,
                    exception: spvitaminError.exception,
                    type: spvitaminError.type,
                    statusText: error.statusText,
                    message: spvitaminError.exception?.message
                };
            }
            else
            {
                this.data = {
                    timestamp: new Date(),
                    status: error?.status,
                    path: error?.url,
                    statusText: error?.statusText,
                    message: error?.error
                };
            }
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
        if (this.data?.exception?.exceptionClass === 'hu.perit.spvitamin.core.exception.ApplicationException' ||
            this.data?.exception?.exceptionClass === 'hu.perit.spvitamin.core.exception.ApplicationRuntimeException')
        {
            return this.data?.message ?? 'Unknown error';
        }

        return '';
    }

    public getErrorDetails(): string
    {
        let details = '';
        if (this.data.error)
        {
            details += `${this.data.error}<br><br>`;
        }
        details += `Status: ${this.data.status} - ${this.inputData.value?.statusText}`;
        details += `<br>Timestamp: ${this.data.timestamp?.toLocaleString('hu')}`;
        details += `<br>Message: ${this.data.message}`;
        details += `<br>Path: ${this.data.path}`;
        if (this.data.traceId)
        {
            details += `<br>TraceId: ${this.data.traceId}`;
        }
        if (this.data.type)
        {
            details += `<br>Type: ${this.data.type}`;
        }
        details += `<br>`;
        if (this.data.exception)
        {
            details += '<br>' + this.getExceptionAsText(this.data.exception, 0);
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

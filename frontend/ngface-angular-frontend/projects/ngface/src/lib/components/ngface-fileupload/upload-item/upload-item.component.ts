import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {MatCard} from '@angular/material/card';
import {MatProgressBar} from '@angular/material/progress-bar';
import {MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {BehaviorSubject, Observable, ReplaySubject, Subscription} from 'rxjs';
import {IUploadEvent, IUploadProgress} from '../ngface-file-upload.type';
import {BytesPipe} from '../../../directives/bytes.pipe';
import {AsyncPipe, NgIf} from '@angular/common';
import {HttpClient, HttpEventType} from '@angular/common/http';

@Component({
  selector: 'upload-item',
  standalone: true,
  imports: [
    MatCard,
    MatProgressBar,
    MatIconButton,
    MatIcon,
    BytesPipe,
    AsyncPipe,
    NgIf
  ],
  templateUrl: './upload-item.component.html',
  styleUrl: './upload-item.component.css'
})
export class UploadItemComponent implements OnInit, OnDestroy
{

  // tslint:disable-next-line:variable-name
  private _file!: File;
  // tslint:disable-next-line:variable-name
  private _id!: number;

  @Input()
  httpUrl!: string;

  @Input()
  get file(): File
  {
    return this._file;
  }

  set file(file: File)
  {
    this._file = file;
  }

  @Input()
  set id(id: number)
  {
    this._id = id;
  }

  get id(): number
  {
    return this._id;
  }

  @Output() removeItem: EventEmitter<UploadItemComponent> = new EventEmitter<UploadItemComponent>();
  @Output() onUpload: EventEmitter<IUploadEvent> = new EventEmitter<IUploadEvent>();

  private uploadProgressSubject: ReplaySubject<IUploadProgress> = new ReplaySubject<IUploadProgress>();
  uploadProgress$: Observable<IUploadProgress> = this.uploadProgressSubject.asObservable();

  private uploadInProgressSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  uploadInProgress$: Observable<boolean> = this.uploadInProgressSubject.asObservable();

  public subs = new Subscription();


  constructor(
    private httpClient: HttpClient,
  )
  {
  }

  ngOnInit(): void
  {
    this.uploadProgressSubject.next({
      progressPercentage: 0,
      loaded: 0,
      total: this._file.size,
    });
  }

  remove(): void
  {
    this.subs.unsubscribe();
    this.removeItem.emit(this);
  }

  public upload(): void
  {
    this.uploadProgressSubject.next({
      progressPercentage: 0,
      loaded: 0,
      total: this._file.size,
    });
    this.uploadInProgressSubject.next(true);
    // How to set the alias?
    const formData: FormData = new FormData();
    formData.append('file', this._file);
    this.subs.add(
      this.httpClient.post(this.httpUrl, formData, {
        observe: 'events',
        reportProgress: true,
        responseType: 'json',
      }).subscribe(
        (event: any) =>
        {
          if (event.type === HttpEventType.UploadProgress)
          {
            this.uploadProgressSubject.next({
              progressPercentage: Math.floor(
                (event.loaded * 100) / event.total
              ),
              loaded: event.loaded,
              total: event.total,
            });
          }
          this.onUpload.emit({file: this._file, event});
        },
        (error: any) =>
        {
          this.uploadInProgressSubject.next(false);
          this.onUpload.emit({file: this._file, event: error});
        },
        () => this.uploadInProgressSubject.next(false)
      )
    );
  }

  ngOnDestroy(): void
  {
    this.subs.unsubscribe();
  }
}
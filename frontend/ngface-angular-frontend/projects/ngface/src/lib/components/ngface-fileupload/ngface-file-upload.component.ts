import {Component, EventEmitter, Input, Output, QueryList, ViewChildren} from '@angular/core';
import {MatIcon} from '@angular/material/icon';
import {MatButton, MatFabButton} from '@angular/material/button';
import {NgForOf, NgIf} from '@angular/common';
import {UploadItemComponent} from './upload-item/upload-item.component';
import {IUploadEvent} from './ngface-file-upload.type';
import {MatBadge} from '@angular/material/badge';

export interface FileStatus
{
  file: File;
  uploaded: boolean;
}

// Adapted this project to my needs: https://github.com/nishantmc/angular-material-fileupload.git

@Component({
  selector: 'ngface-file-upload',
  standalone: true,
  imports: [
    MatIcon,
    MatFabButton,
    MatButton,
    NgIf,
    NgForOf,
    UploadItemComponent,
    MatBadge
  ],
  templateUrl: './ngface-file-upload.component.html',
  styleUrl: './ngface-file-upload.component.css'
})
export class NgfaceFileUploadComponent
{
  @ViewChildren(UploadItemComponent)
  fileUploads!: QueryList<UploadItemComponent>;

  @Input()
  httpUrl!: string;

  @Input()
  uploadAllColor = 'primary';

  @Input()
  uploadAllLabel = 'Upload All';

  @Input()
  removeAllColor = 'primary';

  @Input()
  removeAllLabel = 'Remove All';

  @Input()
  dropZoneText = 'Select or drag and drop files here';

  @Output() onUpload: EventEmitter<IUploadEvent> = new EventEmitter<IUploadEvent>();


  public files: Array<FileStatus> = [];


  onFileInput($event: Event): void
  {
    const target = $event.target as HTMLInputElement;

    if (target.files)
    {
      for (let i = 0; i < target.files.length; i++)
      {
        this.files.push({file: target.files[i], uploaded: false});
        console.log(`${i}: ${target.files[i].name}`);
      }
      console.log(`Added ${this.files.length} files to the queue`);
    }
  }

  uploadAll(): void
  {
    console.log('uploadAll');
    this.fileUploads.forEach((fileUpload) =>
    {
      console.log(fileUpload);
      fileUpload.upload();
    });
  }


  removeAll(): void
  {
    for (let i = 0; i < this.files.length; i++)
    {
      if (!this.files[i].uploaded)
      {
        this.files.splice(i, 1);
      }
    }
  }


  removeItem($event: UploadItemComponent): void
  {
    for (let i = 0; i < this.files.length; i++)
    {
      if ($event.file === this.files[i].file)
      {
        this.files.splice(i, 1);
      }
    }
  }


  emitUpload($event: IUploadEvent)
  {
    for (let i = 0; i < this.files.length; i++)
    {
      if ($event.file === this.files[i].file)
      {
        this.files[i].uploaded = true;
      }
    }
    this.onUpload.emit($event)
  }


  public getCountOpen(): number
  {
    let sum = 0;
    for (let i = 0; i < this.files.length; i++)
    {
      if (!this.files[i].uploaded)
      {
        sum++;
      }
    }
    return sum;
  }


  public getBadge(): string
  {
    let countOpen = this.getCountOpen();
    if (countOpen == 0)
    {
      return "";
    }
    return countOpen.toString();
  }
}

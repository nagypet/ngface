import {Component, EventEmitter, Input, Output, QueryList, ViewChildren} from '@angular/core';
import {MatIcon} from '@angular/material/icon';
import {MatButton, MatFabButton} from '@angular/material/button';
import {NgForOf, NgIf} from '@angular/common';
import {UploadItemComponent} from './upload-item/upload-item.component';
import {IUploadEvent} from './ngface-file-upload.type';


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
    UploadItemComponent
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

  @Output() onUpload: EventEmitter<IUploadEvent> = new EventEmitter<IUploadEvent>();


  public files: Array<File> = [];


  onFileInput($event: Event): void
  {
    const target = $event.target as HTMLInputElement;

    if (target.files)
    {
      for (let i = 0; i < target.files.length; i++)
      {
        this.files.push(target.files[i]);
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
    this.files = [];
  }

  removeItem($event: UploadItemComponent): void
  {
    for (let i = 0; i < this.files.length; i++)
    {
      if ($event.file === this.files[i])
      {
        this.files.splice(i, 1);
      }
    }
  }
}

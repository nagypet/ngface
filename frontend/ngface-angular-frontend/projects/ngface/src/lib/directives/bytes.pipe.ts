import {Pipe, PipeTransform} from '@angular/core';

@Pipe({standalone: true, name: 'bytes'})
export class BytesPipe implements PipeTransform
{
  public transform(bytes?: number): string
  {
    if (!bytes)
    {
      return '0';
    }
    if (isNaN(parseFloat('' + bytes)) || !isFinite(bytes))
    {
      return '-';
    }
    if (bytes <= 0)
    {
      return '0';
    }
    const units = ['bytes', 'KB', 'MB', 'GB', 'TB', 'PB'];
    const value = Math.floor(Math.log(bytes) / Math.log(1024));
    return (bytes / Math.pow(1024, Math.floor(value))).toFixed(1) + ' ' + units[value];
  }
}

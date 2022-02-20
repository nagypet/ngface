import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DemoService
{

  constructor(private httpClient: HttpClient) {}

  private static getServiceUrl(path: string): string
  {
    const host = window.location.hostname;
    const protocol = window.location.protocol;
    const port = window.location.port;

    let url = '';
    if (port === '4200')
    {
      // running on dev environment
      url = 'http://localhost:4200' + path;
    } else
    {
      url = protocol + '//' + host + ':' + port + path;
    }

    console.log('Connecting to \'' + url + '\'');
    return url;
  }


  public getDemoForm(): Observable<any> {
    return this.httpClient.get(DemoService.getServiceUrl('/demo'));
  }

}

import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DemoService
{

  constructor(private httpClient: HttpClient)
  {
  }

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


  public getDemoForm(pageNumber?: number, pageSize?: number): Observable<any>
  {
    let params = new HttpParams();
    if (pageNumber)
    {
      params.set('pageNumber', pageNumber.toString());
    }
    if (pageSize)
    {
      params.set('pageSize', pageSize.toString());
    }

    return this.httpClient.get(DemoService.getServiceUrl('/demo'), {params});
  }


  public submitDemoForm(submitFormData: { widgetDataMap: any }): Observable<any>
  {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.httpClient.post(DemoService.getServiceUrl('/demo'), submitFormData, httpOptions);
  }

}

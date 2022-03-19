import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TypeModels} from '../dto-models';

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


  public getDemoForm(pageNumber?: number, pageSize?: number, sortColumn?: string, sortDirection?: string): Observable<any>
  {
    return this.httpClient.get(DemoService.getServiceUrl('/demo'), {
      params: new HttpParams()
        .set('pageNumber', pageNumber !== undefined ? pageNumber.toString() : '')
        .set('pageSize', pageSize !== undefined ? pageSize.toString() : '')
        .set('sortColumn', sortColumn !== undefined ? sortColumn : '')
        .set('sortDirection', sortDirection !== undefined ? sortDirection : '')
    });
  }


  public submitDemoForm(submitFormData: TypeModels.SubmitFormData): Observable<any>
  {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.httpClient.post(DemoService.getServiceUrl('/demo'), submitFormData, httpOptions);
  }


  public getTableDetailsForm(id: string): Observable<any>
  {
    return this.httpClient.get(DemoService.getServiceUrl('/table-details'), {
      params: new HttpParams()
        .set('id', id)
    });
  }

  submitTableDetailsForm(submitFormData: TypeModels.SubmitFormData): Observable<any>
  {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.httpClient.post(DemoService.getServiceUrl('/table-details'), submitFormData, httpOptions);
  }
}

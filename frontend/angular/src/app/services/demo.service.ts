/*
 * Copyright 2020-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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


  public getDemoForm(pageNumber?: number, pageSize?: number, sortColumn?: string, sortDirection?: string, rowId?:string): Observable<any>
  {
    return this.httpClient.get(DemoService.getServiceUrl('/demo'), {
      params: new HttpParams()
        .set('pageNumber', pageNumber !== undefined ? pageNumber.toString() : '')
        .set('pageSize', pageSize !== undefined ? pageSize.toString() : '')
        .set('sortColumn', sortColumn !== undefined ? sortColumn : '')
        .set('sortDirection', sortDirection !== undefined ? sortDirection : '')
        .set('rowId', rowId !== undefined ? rowId : '')
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

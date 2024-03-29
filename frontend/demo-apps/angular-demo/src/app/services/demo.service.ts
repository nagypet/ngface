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
import {Ngface} from 'ngface/ngface-models';

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

    let url = protocol + '//' + host + ':' + port + path;

    console.log('Connecting to \'' + url + '\'');
    return url;
  }


  public getDemoForm(searchRequest: Ngface.DataRetrievalParams): Observable<any>
  {
    var headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json; charset=utf-8');
    return this.httpClient.post(DemoService.getServiceUrl('/demo/get'), searchRequest, {headers: headers});
  }


  public getDemoFormTableRow(rowId: string): Observable<any>
  {
    var headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json; charset=utf-8');

    return this.httpClient.get(DemoService.getServiceUrl('/demo/get'), {
      headers: headers,
      params: new HttpParams()
        .set('rowId', rowId)
    });
  }


  public submitDemoForm(submitFormData: Ngface.SubmitFormData): Observable<any>
  {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.httpClient.post(DemoService.getServiceUrl('/demo/submit'), submitFormData, httpOptions);
  }


  public getTableDetailsForm(id: string): Observable<any>
  {
    return this.httpClient.get(DemoService.getServiceUrl('/table-details'), {
      params: new HttpParams()
        .set('id', id)
    });
  }

  submitTableDetailsForm(submitFormData: Ngface.SubmitFormData): Observable<any>
  {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.httpClient.post(DemoService.getServiceUrl('/table-details'), submitFormData, httpOptions);
  }
}

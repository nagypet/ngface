/*
 * Copyright 2020-2024 the original author or authors.
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
import {Ngface} from '../../../../../ngface/src/lib/ngface-models';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {environment} from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class TableDemoFormService
{
    private readonly serviceUrl = `/frontend/forms/table-form`;


    constructor(private httpClient: HttpClient)
    {
    }

    public getDemoFormTable(searchRequest?: Ngface.DataRetrievalParams): Observable<any>
    {
        let request = searchRequest;
        if (!request)
        {
            request = {page: null, sort: null, filters: null};
        }
        const headers = new HttpHeaders();
        headers.append('Content-Type', 'application/json; charset=utf-8');
        return this.httpClient.post(`${environment.baseURL}${this.serviceUrl}/get-table`, request, {headers});
    }


    public getDemoFormTableRow(rowId: number): Observable<any>
    {
        const headers = new HttpHeaders();
        headers.append('Content-Type', 'application/json; charset=utf-8');

        return this.httpClient.get(`${environment.baseURL}${this.serviceUrl}`, {
            headers,
            params: new HttpParams()
                .set('rowId', rowId)
        });
    }


    public submitDemoFormTable(submitFormData: Ngface.SubmitFormData): Observable<any>
    {
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            })
        };
        return this.httpClient.post(`${environment.baseURL}${this.serviceUrl}`, submitFormData, httpOptions);
    }


    public onRowSelect(rowSelectParams: Ngface.RowSelectParams<number>): Observable<any>
    {
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            })
        };
        return this.httpClient.put(`${environment.baseURL}${this.serviceUrl}/row-select`, rowSelectParams, httpOptions);
    }


    public getColumnFilterer(column: string, searchText: string): Observable<any>
    {
        return this.httpClient.get(`${environment.baseURL}${this.serviceUrl}/filterer`, {
            params: new HttpParams()
                .set('column', column)
                .set('searchText', searchText)
        });
    }

    public reloadTableData(): Observable<any>
    {
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            })
        };
        return this.httpClient.post(`${environment.baseURL}/frontend/addresses/reset`, httpOptions);
    }
}

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
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Ngface} from '../../../../../ngface/src/lib/ngface-models';

@Injectable({
    providedIn: 'root'
})
export class TableDetailsService
{
    private readonly serviceUrl = `/frontend/forms/table-details-form`;

    constructor(private httpClient: HttpClient)
    {
    }

    public getTableDetailsForm(id: number): Observable<any>
    {
        return this.httpClient.get(`${environment.baseURL}${this.serviceUrl}`, {
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
        return this.httpClient.post(`${environment.baseURL}${this.serviceUrl}`, submitFormData, httpOptions);
    }

}

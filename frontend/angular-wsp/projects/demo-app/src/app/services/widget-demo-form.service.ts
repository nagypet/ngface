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
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Ngface} from '../../../../ngface/src/lib/ngface-models';
import {environment} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class WidgetDemoFormService
{
    private readonly serviceUrl = `/frontend/forms/demo/widgets`;

    constructor(private httpClient: HttpClient)
    {
    }


    public getDemoForm(): Observable<any>
    {
        return this.httpClient.get(`${environment.baseURL}${this.serviceUrl}`);
    }


    public submitDemoForm(submitFormData: Ngface.SubmitFormData): Observable<any>
    {
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            })
        };
        return this.httpClient.post(`${environment.baseURL}${this.serviceUrl}`, submitFormData, httpOptions);
    }
}

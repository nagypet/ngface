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

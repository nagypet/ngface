import {Injectable} from '@angular/core';
import {Ngface} from '../../../../ngface/src/lib/ngface-models';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class TableDemoFormService
{
    private readonly serviceUrl = `/frontend/forms/demo/table`;


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
        return this.httpClient.post(`${environment.baseURL}${this.serviceUrl}/get`, request, {headers});
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

import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TitlebarService {

  private readonly serviceUrl = `/frontend/forms/titlebar`;

  constructor(private httpClient: HttpClient)
  {
  }


  public getTitlebar(): Observable<any>
  {
    return this.httpClient.get(`${environment.baseURL}${this.serviceUrl}`);
  }
}

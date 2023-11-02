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

import {enableProdMode, importProvidersFrom, LOCALE_ID} from '@angular/core';
import localeDe from '@angular/common/locales/de';
import localeDeExtra from '@angular/common/locales/extra/de';


import {environment} from './environments/environment';
import {AppComponent} from './app/app.component';
import {A11yModule} from '@angular/cdk/a11y';
import {MatDialogModule} from '@angular/material/dialog';
import {provideHttpClient, withInterceptorsFromDi} from '@angular/common/http';
import {NgfaceModule} from '../../ngface/src/lib/ngface.module';
import {bootstrapApplication, BrowserModule} from '@angular/platform-browser';
import {MAT_DATE_LOCALE} from '@angular/material/core';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from '@angular/material/form-field';
import {registerLocaleData} from '@angular/common';
import {provideRouter, withHashLocation} from '@angular/router';
import {APP_ROUTES} from './app/app-routing.module';

registerLocaleData(localeDe, 'de-DE', localeDeExtra);

if (environment.production)
{
  enableProdMode();
}

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(BrowserModule, NgfaceModule, MatDialogModule, A11yModule),
    provideRouter(APP_ROUTES, withHashLocation()),
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'fill'}},
    {provide: MAT_DATE_LOCALE, useValue: 'hu'},
    {provide: LOCALE_ID, useValue: 'de-DE'},
    provideHttpClient(withInterceptorsFromDi())
  ]
})
  .catch(err => console.error(err));

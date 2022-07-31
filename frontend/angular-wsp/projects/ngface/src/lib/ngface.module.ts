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

import {NgModule} from '@angular/core';
import {NgfaceButtonComponent} from './ngface-button/ngface-button.component';
import {CommonModule} from '@angular/common';
import {MatTooltipModule} from '@angular/material/tooltip';
import {NgfaceDataTableComponent} from './ngface-data-table/ngface-data-table.component';
import {NgfaceDateInputComponent} from './ngface-date-input/ngface-date-input.component';
import {NgfaceDateRangeInputComponent} from './ngface-date-range-input/ngface-date-range-input.component';
import {NgfaceNumericInputComponent} from './ngface-numeric-input/ngface-numeric-input.component';
import {NgfaceSelectComponent} from './ngface-select/ngface-select.component';
import {NgfaceTextInputComponent} from './ngface-text-input/ngface-text-input.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {IntlNumericInputComponent} from './ngface-numeric-input/intl-numeric-input/intl-numeric-input.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatTableModule} from '@angular/material/table';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {SortFilterHeaderComponent} from './ngface-data-table/sort-filter-header/sort-filter-header.component';
import {MatIconModule} from '@angular/material/icon';
import {ExcelFilterComponent} from './ngface-data-table/excel-filter/excel-filter.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatNativeDateModule} from '@angular/material/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BrowserModule} from '@angular/platform-browser';
import {MatSortModule} from '@angular/material/sort';
import {MatButtonModule} from '@angular/material/button';
import {NumericInputFilterDirective} from './directives/NumericInputFilterDirective';
import {DebounceInputDirective} from './directives/debounce-input-directive';
import {NgScrollbarModule} from 'ngx-scrollbar';
import {MatBadgeModule} from '@angular/material/badge';


@NgModule({
  declarations: [
    NgfaceButtonComponent,
    NgfaceDataTableComponent,
    NgfaceDateInputComponent,
    NgfaceDateRangeInputComponent,
    NgfaceNumericInputComponent,
    NgfaceSelectComponent,
    NgfaceTextInputComponent,
    IntlNumericInputComponent,
    SortFilterHeaderComponent,
    ExcelFilterComponent,
    NumericInputFilterDirective,
    DebounceInputDirective,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    CommonModule,
    MatTooltipModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatBadgeModule,
    FormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTableModule,
    MatCheckboxModule,
    MatIconModule,
    MatPaginatorModule,
    MatSortModule,
    NgScrollbarModule,
  ],
  exports: [
    NgfaceButtonComponent,
    NgfaceDataTableComponent,
    NgfaceDateInputComponent,
    NgfaceDateRangeInputComponent,
    NgfaceNumericInputComponent,
    NgfaceSelectComponent,
    NgfaceTextInputComponent,
  ]
})
export class NgfaceModule
{
}

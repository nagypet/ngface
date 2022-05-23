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
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    CommonModule,
    MatTooltipModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatSelectModule,
    FormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTableModule,
    MatCheckboxModule,
    MatIconModule,
    MatPaginatorModule,
    MatSortModule,
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
export class NgfaceModule { }

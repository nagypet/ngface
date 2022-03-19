import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {TextInputComponent} from './ngface/text-input/text-input.component';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatIconModule} from '@angular/material/icon';
import {MatTooltipModule} from '@angular/material/tooltip';
import {ButtonComponent} from './ngface/button/button.component';
import {MatButtonModule} from '@angular/material/button';
import {NumericInputComponent} from './ngface/numeric-input/numeric-input.component';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS, MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {DemoForm1Component} from './demo-form1/demo-form1.component';
import {DateInputComponent} from './ngface/date-input/date-input.component';
import {MAT_DATE_LOCALE, MatNativeDateModule, MatOptionModule} from '@angular/material/core';
import {DateRangeInputComponent} from './ngface/date-range-input/date-range-input.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {SelectComponent} from './ngface/select/select.component';
import {MatSelectModule} from '@angular/material/select';
import {MatTableModule} from '@angular/material/table';
import {DataTableComponent} from './ngface/data-table/data-table.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import { DemoDialog1Component } from './demo-dialog1/demo-dialog1.component';
import {MatDialogModule} from '@angular/material/dialog';

@NgModule({
  declarations: [
    AppComponent,
    TextInputComponent,
    ButtonComponent,
    NumericInputComponent,
    DemoForm1Component,
    DateInputComponent,
    DateRangeInputComponent,
    SelectComponent,
    DataTableComponent,
    DemoDialog1Component
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatIconModule,
    MatTooltipModule,
    MatButtonModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatOptionModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    FormsModule,
    MatDialogModule,
  ],
  providers: [
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'fill'}},
    {provide: MAT_DATE_LOCALE, useValue: 'hu'},
  ],
  bootstrap: [AppComponent]
})
export class AppModule
{
}

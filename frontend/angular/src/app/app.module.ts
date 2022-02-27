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
import {ReactiveFormsModule} from '@angular/forms';
import {DemoForm1Component} from './demo-form1/demo-form1.component';
import {DateInputComponent} from './ngface/date-input/date-input.component';
import {MAT_DATE_LOCALE, MatNativeDateModule} from '@angular/material/core';
import { DateRangeInputComponent } from './ngface/date-range-input/date-range-input.component';
import {MatDatepickerModule} from '@angular/material/datepicker';

@NgModule({
  declarations: [
    AppComponent,
    TextInputComponent,
    ButtonComponent,
    NumericInputComponent,
    DemoForm1Component,
    DateInputComponent,
    DateRangeInputComponent
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
  ],
  providers: [
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'outline'}},
    {provide: MAT_DATE_LOCALE, useValue: 'hu'},
  ],
  bootstrap: [AppComponent]
})
export class AppModule
{
}

import {Injector, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CommonModule} from '@angular/common';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatSelectModule} from '@angular/material/select';
import {MatBadgeModule} from '@angular/material/badge';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {MatTableModule} from '@angular/material/table';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatIconModule} from '@angular/material/icon';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import {NgScrollbarModule} from 'ngx-scrollbar';
import {createCustomElement} from '@angular/elements';
import {NgfaceButtonComponent} from './ngface-button/ngface-button.component';
import {NgfaceTextInputComponent} from './ngface-text-input/ngface-text-input.component';

@NgModule({
  declarations: [
    AppComponent,
    NgfaceButtonComponent,
    NgfaceTextInputComponent
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
  providers: [],
  bootstrap: [AppComponent, NgfaceButtonComponent, NgfaceTextInputComponent]
})
export class AppModule
{
  constructor(private injector: Injector)
  {
    const buttonElement = createCustomElement(NgfaceButtonComponent, {injector});
    customElements.define('ngface-button', buttonElement);
    const textInputElement = createCustomElement(NgfaceTextInputComponent, {injector});
    customElements.define('ngface-text-input', textInputElement);
  }

  ngDoBootstrap()
  {
  }
}

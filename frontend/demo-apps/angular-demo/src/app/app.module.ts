import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {DemoForm1Component} from './demo-form1/demo-form1.component';
import {HttpClientModule} from '@angular/common/http';
import {MatDialogModule} from '@angular/material/dialog';
//import {DemoDialog1Component} from './demo-dialog1/demo-dialog1.component';

@NgModule({
  declarations: [
    AppComponent,
    DemoForm1Component,
    //DemoDialog1Component
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    MatDialogModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule
{
}

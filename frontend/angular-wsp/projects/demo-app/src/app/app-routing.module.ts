import {Routes} from '@angular/router';
import {DemoForm1Component} from './demo-form1/demo-form1.component';
import {DemoForm2Component} from './demo-form2/demo-form2.component';


export const APP_ROUTES: Routes = [
    {path: '', redirectTo: '/demo-form1', pathMatch: 'full'},
    {path: 'demo-form1', component: DemoForm1Component},
    {path: 'demo-form2', component: DemoForm2Component}
];

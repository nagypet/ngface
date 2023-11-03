import {Routes} from '@angular/router';
import {TableDemoFormComponent} from './table-demo-form/table-demo-form.component';
import {WidgetDemoFormComponent} from './widget-demo-form/widget-demo-form.component';


export const APP_ROUTES: Routes = [
    {path: '', redirectTo: 'widget-demo', pathMatch: 'full'},
    {path: 'widget-demo', component: WidgetDemoFormComponent},
    {path: 'table-demo', component: TableDemoFormComponent}
];

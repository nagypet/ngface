import {Routes} from '@angular/router';
import {TableDemoFormComponent} from './ui/table-demo-form/table-demo-form.component';
import {WidgetDemoFormComponent} from './ui/widget-demo-form/widget-demo-form.component';


export const APP_ROUTES: Routes = [
    {path: '', redirectTo: 'widget-demo', pathMatch: 'full'},
    {path: 'widget-demo', component: WidgetDemoFormComponent},
    {path: 'table-demo', component: TableDemoFormComponent}
];

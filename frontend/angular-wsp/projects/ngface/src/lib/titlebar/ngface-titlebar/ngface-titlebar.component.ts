import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {Ngface} from '../../ngface-models';
import {NgFor} from '@angular/common';
import {AutocompleteRequest} from '../../widgets/ngface-autocomplete/ngface-autocomplete.component';
import {MatMenuModule} from '@angular/material/menu';

@Component({
    selector: 'ngface-titlebar',
    templateUrl: './ngface-titlebar.component.html',
    styleUrls: ['./ngface-titlebar.component.css'],
    imports: [
        MatToolbarModule,
        MatIconModule,
        MatButtonModule,
        NgFor,
        MatMenuModule
    ],
    standalone: true
})
export class NgfaceTitlebarComponent
{
    @Input()
    formdata?: Ngface.Form;

    @Input()
    widgetid = '';

    @Output()
    menuItemClick: EventEmitter<Ngface.Menu.Item> = new EventEmitter();

    @Output()
    actionClick: EventEmitter<Ngface.Action> = new EventEmitter();

    getData(): Ngface.Titlebar
    {
        const widget = this.formdata?.widgets[this.widgetid];
        if (!widget || widget?.type !== 'Titlebar')
        {
            return {
                type: 'Titlebar',
                appTitle: 'App title',
                menu: {items: []},
                data: {type: 'VoidWidgetData'},
                actions: [],
                label: 'undefined label',
                enabled: false,
                id: '',
                hint: ''
            };
        }
        return this.formdata?.widgets[this.widgetid] as Ngface.Titlebar;
    }

    onActionClick(action: Ngface.Action): void
    {
        this.actionClick.emit(action);
    }

    onMenuClick(menuItem: Ngface.Menu.Item): void
    {
        this.menuItemClick.emit(menuItem);
    }
}

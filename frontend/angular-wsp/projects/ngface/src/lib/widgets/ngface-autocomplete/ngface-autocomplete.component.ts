import {Component, EventEmitter, OnChanges, Output, SimpleChange} from '@angular/core';
import {InputBaseComponent} from '../input-base.component';
import {Ngface} from '../../ngface-models';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatOptionModule} from '@angular/material/core';
import {MatSelectModule} from '@angular/material/select';
import {AsyncPipe, NgForOf, NgIf} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatInputModule} from '@angular/material/input';
import {DebounceInputDirective} from '../../directives/debounce-input-directive';
import {A11yModule} from '@angular/cdk/a11y';
import {map, Observable, startWith, Subject} from 'rxjs';

export interface AutocompleteRequest
{
    widgetId: string;
    searchText: string;
    filteredOptions: Observable<string[]>;
}

@Component({
    selector: 'ngface-autocomplete',
    templateUrl: './ngface-autocomplete.component.html',
    styleUrls: ['./ngface-autocomplete.component.css'],
    imports: [
        MatFormFieldModule,
        MatOptionModule,
        MatSelectModule,
        NgForOf,
        NgIf,
        ReactiveFormsModule,
        MatAutocompleteModule,
        MatInputModule,
        AsyncPipe,
        DebounceInputDirective,
        A11yModule,
    ],
    standalone: true
})
export class NgfaceAutocompleteComponent extends InputBaseComponent implements OnChanges
{
    @Output()
    onAutocompleteRequest: EventEmitter<AutocompleteRequest> = new EventEmitter();

    searchText: Subject<string> = new Subject<string>();
    filteredOptions: Observable<string[]> = new Observable<string[]>();

    constructor()
    {
        super();
    }


    ngOnChanges(changes: { [propName: string]: SimpleChange }): void
    {
        super.ngOnChanges(changes);
        if (!this.getData().data.extendedReadOnlyData.valueSet.remote)
        {
            this.filteredOptions = this.searchText.pipe(
                startWith(''),
                map(value => this.filter(value || '')),
            );
        }
    }


    getData(): Ngface.Autocomplete
    {
        const widget = this.formdata?.widgets[this.widgetid];
        if (!widget || widget?.type !== 'Autocomplete')
        {
            return {
                type: 'Autocomplete',
                data: {type: 'Autocomplete.Data', value: '', extendedReadOnlyData: {valueSet: {remote: false, truncated: false, values: []}}},
                placeholder: 'widget id: ' + this.widgetid,
                label: 'undefined label',
                validators: [],
                enabled: false,
                id: '',
                hint: ''
            };
        }
        return this.formdata?.widgets[this.widgetid] as Ngface.Autocomplete;
    }

    private filter(value: string): string[]
    {
        const filterValue = value.toLowerCase();

        return this.getData().data.extendedReadOnlyData.valueSet.values
            .map(i => i.text)
            .filter(item => item.toLowerCase().includes(filterValue));
    }

    onSearchTextChange($event: string): void
    {
        this.searchText.next($event);
        if (this.getData().data.extendedReadOnlyData.valueSet.remote)
        {
            this.onAutocompleteRequest.emit({widgetId: this.widgetid, searchText: $event, filteredOptions: this.filteredOptions});
        }
    }
}

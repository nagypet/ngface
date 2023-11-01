import {Component, OnChanges, OnInit} from '@angular/core';
import {InputBaseComponent} from '../input-base.component';
import {Ngface} from '../../ngface-models';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatOptionModule} from '@angular/material/core';
import {MatSelectModule} from '@angular/material/select';
import {AsyncPipe, NgForOf, NgIf} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';
import {map, Observable, startWith} from 'rxjs';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatInputModule} from '@angular/material/input';

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
    AsyncPipe
  ],
  standalone: true
})
export class NgfaceAutocompleteComponent extends InputBaseComponent implements OnInit, OnChanges
{
  filteredOptions?: Observable<string[]>;

  constructor()
  {
    super();
  }

  ngOnInit(): void
  {
    this.filteredOptions = this.formControl.valueChanges.pipe(
      startWith(''),
      map(value => this.filter(value || '')),
    );
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
}

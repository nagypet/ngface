import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {FormControl} from '@angular/forms';
import {TypeModels} from '../../../dto-models';

export interface SearchItem
{
  text: string;
  selected: boolean;
}


export interface SearchEvent
{
  searchText: string;
  searchResultProvider: SearchResultProvider;
}

export class SearchResultProvider
{
  private _choises: SearchItem[] = [];
  get choises(): SearchItem[]
  {
    return this._choises;
  }
  set choises(value: SearchItem[])
  {
    this._choises = value;
  }

  constructor(filter?: TypeModels.Filter)
  {
    if (filter)
    {
      filter.filters.forEach(f => this._choises.push({text: f, selected: false}));
    }
  }
}


@Component({
  selector: 'ngface-excel-filter',
  templateUrl: './excel-filter.component.html',
  styleUrls: ['./excel-filter.component.scss']
})
export class ExcelFilterComponent implements OnInit, OnChanges
{
  formControlSearch = new FormControl('', []);

  @Input()
  filter: TypeModels.Filter | undefined;

  @Output()
  searchEvent: EventEmitter<SearchEvent> = new EventEmitter();

  searchResultProvider: SearchResultProvider = new SearchResultProvider();

  constructor()
  {
  }

  ngOnInit(): void
  {
  }

  ngOnChanges(): void
  {
    this.searchResultProvider = new SearchResultProvider(this.filter);
  }

  onItemSelected(choice: SearchItem)
  {
    choice.selected = !choice.selected;
  }

  onCancel()
  {

  }

  onOk()
  {

  }

  onChange($event: string)
  {
    this.searchEvent.emit({searchText: $event, searchResultProvider: this.searchResultProvider});
  }

}

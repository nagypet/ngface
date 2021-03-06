import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {FormControl} from '@angular/forms';
import {TypeModels} from '../../../dto-models';
import {FilterCriteriaProvider} from './filter-criteria-provider';

export interface FilterCriteriaItem
{
  masterSelect: boolean;
  text: string;
  selected: boolean;
}


export interface SearchEvent
{
  searchText: string;
  filterCriteriaProvider: FilterCriteriaProvider;
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

  filterCriteriaProvider: FilterCriteriaProvider = new FilterCriteriaProvider();

  constructor()
  {
  }

  ngOnInit(): void
  {
  }

  ngOnChanges(): void
  {
    this.filterCriteriaProvider = new FilterCriteriaProvider(this.filter);
  }

  onItemSelected(choice: FilterCriteriaItem, b?: boolean)
  {
    let newValue = b ?? !choice.selected;
    if (choice.masterSelect)
    {
      this.filterCriteriaProvider.selectAll(newValue);
    } else
    {
      choice.selected = newValue;
    }
  }

  onCheckBoxClicked(choice: FilterCriteriaItem, $event: boolean)
  {
    this.onItemSelected(choice, $event);
  }

  onCancel()
  {

  }

  onOk()
  {

  }

  onChange($event: string)
  {
    if (this.filter?.remote)
    {
      this.searchEvent.emit({searchText: $event, filterCriteriaProvider: this.filterCriteriaProvider});
    } else
    {
      this.filterCriteriaProvider.searchText = $event;
    }
  }

  isAnySelected(criteria: FilterCriteriaItem): boolean
  {
    if (!criteria.masterSelect)
    {
      return criteria.selected;
    } else
    {
      return this.filterCriteriaProvider.isAnySelected();
    }
  }

  isAllSelected(criteria: FilterCriteriaItem): boolean
  {
    if (!criteria.masterSelect)
    {
      return criteria.selected;
    } else
    {
      return this.filterCriteriaProvider.isAllSelected();
    }
  }

  isCheckBoxEnabled(criteria: FilterCriteriaItem)
  {
    if (!criteria.masterSelect)
    {
      return true;
    } else
    {
      return this.filterCriteriaProvider.criteria.find(c => !c.masterSelect);
    }
  }
}

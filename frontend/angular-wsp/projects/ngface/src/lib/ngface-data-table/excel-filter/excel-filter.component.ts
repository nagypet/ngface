/*
 * Copyright 2020-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {FormControl} from '@angular/forms';
import {TypeModels} from '../../dto-models';
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

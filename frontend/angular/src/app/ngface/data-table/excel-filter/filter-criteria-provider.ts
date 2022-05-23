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

import {TypeModels} from '../../../dto-models';
import {FilterCriteriaItem} from './excel-filter.component';

export class FilterCriteriaProvider
{
  // criteria
  private _criteria: FilterCriteriaItem[] = [];

  setCriteria(value: string[] | undefined)
  {
    this._criteria = [];
    this._criteria.push({masterSelect: true, text: '(Select All)', selected: true});
    if (value)
    {
      value.forEach(c => this._criteria.push({masterSelect: false, text: c, selected: true}));
    }
  }

  get criteria(): FilterCriteriaItem[]
  {
    return this.applySearchText();
  }

  // searchText
  private _searchText: string | null;
  set searchText(value: string | null)
  {
    this._searchText = value;
  }

  get searchText(): string | null
  {
    return this._searchText;
  }

  private remote = false;

  constructor(filter?: TypeModels.Filter)
  {
    this.setCriteria(filter?.criteria);
    this.remote = filter?.remote ?? false;
  }

  // Filter _criteria by searchText
  private applySearchText(): FilterCriteriaItem[]
  {
    return this._criteria.filter(c => c.masterSelect || this.contains(c.text, this._searchText));
  }

  // returns true if text contains searchTerm
  private contains(text: string, searchTerm: string | null): boolean
  {
    if (!searchTerm)
    {
      return true;
    }
    return text.toLowerCase().indexOf(searchTerm.toLowerCase()) >= 0;
  }

  selectAll(b: boolean)
  {
    this._criteria.forEach(c => c.selected = b);
  }


  isAnySelected()
  {
    return !!this._criteria
      .filter(r => !r.masterSelect)
      .find(r => r.selected);
  }

  isAllSelected()
  {
    if (this.remote)
    {
      return false;
    }
    return (this._criteria
        .filter(r => !r.masterSelect)
        .filter(r => !r.selected).length === 0)
      && (this.applySearchText().length === this._criteria.length);
  }
}

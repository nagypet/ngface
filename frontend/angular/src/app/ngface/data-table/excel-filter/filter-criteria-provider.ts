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
import {SearchItem} from './excel-filter.component';

export class FilterCriteriaProvider
{
  // criteria
  private _criteria: SearchItem[] = [];
  set criteria(value: SearchItem[])
  {
    this._criteria = value;
  }

  get criteria(): SearchItem[]
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


  constructor(filter?: TypeModels.Filter)
  {
    if (filter)
    {
      filter.criteria.forEach(f => this._criteria.push({text: f, selected: false}));
    }
  }

  // Filter _criteria by searchText
  private applySearchText(): SearchItem[]
  {
    return this._criteria.filter(c => this.contains(c.text, this._searchText));
  }

  // returns true if text contains searchTerm
  private contains(text: string, searchTerm: string | null): boolean
  {
    if (!searchTerm)
    {
      return true;
    }
    return text.toLowerCase().indexOf(searchTerm.toLowerCase()) >= 0
  }
}

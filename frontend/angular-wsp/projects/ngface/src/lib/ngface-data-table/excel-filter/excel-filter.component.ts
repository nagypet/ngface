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

import {Component, ElementRef, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {FormControl} from '@angular/forms';
import {ValueSetProvider} from './value-set-provider';
import {Ngface} from "../../ngface-models";

export interface ValueSetItem
{
  masterSelect: boolean;
  text: string;
  selected: boolean;
  selectable: boolean;
}


export interface ValueSetSearchEvent
{
  searchText: string;
  valueSetProvider: ValueSetProvider;
}


export interface FilterChangeEvent
{
  filterer?: Ngface.Filterer;
  changed: boolean;
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
  filterer?: Ngface.Filterer;

  @Output()
  valueSetSearchEvent: EventEmitter<ValueSetSearchEvent> = new EventEmitter();

  @Output()
  filtererChangeEvent: EventEmitter<FilterChangeEvent> = new EventEmitter();

  @Output()
  onCloseEvent: EventEmitter<void> = new EventEmitter();

  valueSetProvider: ValueSetProvider = new ValueSetProvider();

  constructor(private el: ElementRef)
  {
  }

  ngOnInit(): void
  {
    this.moveIfClipped();

    if (this.filterer?.active)
    {
      this.formControlSearch.setValue(this.filterer?.searchText);
      this.valueSetProvider = new ValueSetProvider(this.filterer);
    }
    else
    {
      this.formControlSearch.setValue('');
      this.valueSetProvider = new ValueSetProvider(this.filterer);
      this.valueSetProvider.clearFilter();
      this.reloadValueSetFromServer('');
    }
  }


  private moveIfClipped()
  {
    let headerRow = this.el.nativeElement.parentNode.parentNode;
    let excelFilter = this.el.nativeElement.childNodes[0];
    let excelFilterContainer = this.el.nativeElement.childNodes[0].childNodes[0];

    /*
    console.log(headerRow);
    console.log(excelFilter);
    console.log(excelFilterContainer);
    console.log(headerRow.getBoundingClientRect());
    console.log(excelFilter.getBoundingClientRect());
    console.log(excelFilterContainer.getBoundingClientRect());
    */

    // Top
    let yOffset = headerRow.getBoundingClientRect().height - (excelFilter.getBoundingClientRect().y - headerRow.getBoundingClientRect().y) - 1;
    excelFilterContainer.style.top = yOffset.toString() + 'px';

    // Width
    if (excelFilter.getBoundingClientRect().width * 0.9 > 200)
    {
      excelFilterContainer.style.width = (excelFilter.getBoundingClientRect().width * 0.9).toString() + 'px';
    }

    // Right
    let offset = excelFilter.getBoundingClientRect().x - headerRow.getBoundingClientRect().x;
    let width = excelFilter.getBoundingClientRect().width;
    console.log(`offset: ${offset}, w: ${width}`)

    if (offset + width < 200)
    {
      let shiftPixels = offset + width - 200 + 1;
      excelFilterContainer.style.right = shiftPixels.toString() + 'px';
    }
  }


  ngOnChanges(): void
  {
  }

  onItemSelected(choice: ValueSetItem, b?: boolean)
  {
    let newValue = b ?? !choice.selected;
    if (choice.masterSelect)
    {
      this.valueSetProvider.selectAll(newValue);
    }
    else
    {
      choice.selected = newValue;
    }
  }

  onCheckBoxClicked(choice: ValueSetItem, $event: boolean)
  {
    this.onItemSelected(choice, $event);
  }

  onCancel()
  {
    this.onCloseEvent.emit();
  }

  onOk()
  {
    if (this.filterer)
    {
      let newFilterer = this.getNewFilterer();
      this.filtererChangeEvent.emit({filterer: newFilterer, changed: this.valueSetProvider.isChanged()});
      if (!this.filterer.valueSet.remote)
      {
        this.filterer = newFilterer;
      }
    }
  }


  private getNewFilterer(): Ngface.Filterer | undefined
  {
    if (this.filterer)
    {
      let newFilterer: Ngface.Filterer = {
        active: this.filterer.active,
        column: this.filterer.column,
        searchText: this.formControlSearch.value,
        valueSet: {
          values: this.valueSetProvider.valueSetItems.filter(i => !i.masterSelect && i.selectable),
          truncated: this.valueSetProvider.truncated,
          remote: this.filterer.valueSet.remote
        }
      };
      return newFilterer;
    }

    return undefined;
  }


  onSearchTextChange($event: string)
  {
    this.valueSetProvider.searchText = $event;
    this.reloadValueSetFromServer($event);
  }

  isAnySelected(valueSetItem: ValueSetItem): boolean
  {
    if (!valueSetItem.masterSelect)
    {
      return valueSetItem.selected;
    }
    else
    {
      return this.valueSetProvider.isAnySelected();
    }
  }

  isAllSelected(valueSetItem: ValueSetItem): boolean
  {
    if (!valueSetItem.masterSelect)
    {
      return valueSetItem.selected;
    }
    else
    {
      return this.valueSetProvider.isAllSelected();
    }
  }

  isCheckBoxEnabled(valueSetItem: ValueSetItem)
  {
    if (!valueSetItem.masterSelect)
    {
      return true;
    }
    else
    {
      return this.valueSetProvider.getVisibleItems().find(c => !c.masterSelect);
    }
  }

  onClearFilter()
  {
    if (this.filterer)
    {
      this.formControlSearch.setValue('');
      this.valueSetProvider.clearFilter();
      this.reloadValueSetFromServer('');
    }
  }


  private reloadValueSetFromServer(searchText: string)
  {
    if (this.filterer?.valueSet.remote)
    {
      this.valueSetSearchEvent.emit({searchText: searchText, valueSetProvider: this.valueSetProvider});
    }
  }
}

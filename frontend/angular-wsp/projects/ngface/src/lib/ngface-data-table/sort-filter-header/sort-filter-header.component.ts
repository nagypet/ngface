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

import {Component, ElementRef, EventEmitter, HostListener, Input, OnChanges, OnInit, Output} from '@angular/core';
import {FilterChangeEvent, ValueSetSearchEvent} from '../excel-filter/excel-filter.component';
import {Ngface} from '../../ngface-models';
import {SortDirection} from "@angular/material/sort";

@Component({
  exportAs: 'ngFaceSortFilterHeader',
  selector: '[ngface-sort-filter-header]',
  templateUrl: './sort-filter-header.component.html',
  styleUrls: ['./sort-filter-header.component.scss']
})
export class SortFilterHeaderComponent implements OnInit, OnChanges
{

  @Input()
  text: string;

  @Input()
  sortable: boolean;

  @Input()
  sorter?: Ngface.Sorter;

  @Input()
  filterable: boolean;

  @Input()
  filterer?: Ngface.Filterer;

  @Output()
  searchEvent: EventEmitter<ValueSetSearchEvent> = new EventEmitter();

  @Output()
  filtererChangeEvent: EventEmitter<Ngface.Filterer> = new EventEmitter();

  showExcelFilter = false;

  constructor(private el: ElementRef)
  {
  }

  ngOnInit(): void
  {
  }

  ngOnChanges(): void
  {
  }

  onFilterIconClick($event: MouseEvent)
  {
    this.showExcelFilter = !this.showExcelFilter;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick($event: any)
  {
    if (this.el.nativeElement.childNodes[0].children[1] !== $event.path[1])
    {
      this.showExcelFilter = false;
    }
  }

  onEscape()
  {
    this.showExcelFilter = false;
  }

  onValueSetSearch($event: ValueSetSearchEvent)
  {
    this.searchEvent.emit($event);
  }

  onFiltererChange($event: FilterChangeEvent)
  {
    console.log($event);
    this.filterer = $event.filterer;
    if (this.filterer)
    {
      this.filterer.active = $event.changed;
    }
    this.showExcelFilter = false;
    this.filtererChangeEvent.emit($event.changed ? $event.filterer : undefined);
  }

  onExcelFilterClose()
  {
    this.showExcelFilter = false;
  }

  getClass(): string
  {
    return this.filterer?.active ? 'ngface-filter-header-set' : '';
  }
}

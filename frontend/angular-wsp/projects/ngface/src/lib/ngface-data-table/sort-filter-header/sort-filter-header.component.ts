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

import {Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
import {SearchEvent} from '../excel-filter/excel-filter.component';
import {TypeModels} from '../../dto-models';

@Component({
  exportAs: 'ngFaceSortFilterHeader',
  selector: '[ngface-sort-filter-header]',
  templateUrl: './sort-filter-header.component.html',
  styleUrls: ['./sort-filter-header.component.scss']
})
export class SortFilterHeaderComponent implements OnInit
{

  @Input()
  text: string;

  @Input()
  sortable: boolean;

  @Input()
  filterable: boolean;

  @Input()
  filter: TypeModels.Filter | undefined;

  @Output()
  searchEvent: EventEmitter<SearchEvent> = new EventEmitter();

  showExcelFilter = false;

  constructor(private el: ElementRef)
  {
  }

  ngOnInit(): void
  {
  }

  onClick($event: MouseEvent)
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

  onSearchEvent($event: SearchEvent)
  {
    this.searchEvent.emit($event);
  }
}

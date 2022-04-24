import {Component, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
import {SearchEvent} from '../excel-filter/excel-filter.component';
import {TypeModels} from '../../../dto-models';

@Component({
  exportAs: 'ngFaceSortFilterHeader',
  selector: '[ngface-sort-filter-header]',
  templateUrl: './sort-filter-header.component.html',
  styleUrls: ['./sort-filter-header.component.scss']
})
export class SortFilterHeaderComponent implements OnInit {

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

  constructor() {
  }

  ngOnInit(): void {
  }

  onClick()
  {
    this.showExcelFilter = !this.showExcelFilter;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick($event: any)
  {
    this.showExcelFilter = false;
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

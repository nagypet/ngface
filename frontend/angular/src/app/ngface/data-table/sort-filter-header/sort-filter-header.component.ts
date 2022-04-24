import {Component, HostListener, Input, OnInit} from '@angular/core';

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
}

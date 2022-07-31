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

import {AfterViewInit, Component, ElementRef, EventEmitter, Inject, Input, LOCALE_ID, OnChanges, Output, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort, SortDirection} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {DataTableDataSource} from './data-table-datasource';
import {tap} from 'rxjs/operators';
import {merge} from 'rxjs';
import {MatCheckboxChange} from '@angular/material/checkbox';
import {NumericFormatter} from '../numeric-formatter';
import {ValueSetSearchEvent} from './excel-filter/excel-filter.component';
import {Ngface} from '../ngface-models';
import Form = Ngface.Form;
import ActionCell = Ngface.ActionCell;
import NumericCell = Ngface.NumericCell;
import DataRetrievalParams = Ngface.DataRetrievalParams;

export interface TableReloadEvent
{
  page: Ngface.DataRetrievalParams.Page;
  sort: Ngface.DataRetrievalParams.Sort;
  filter?: Ngface.DataRetrievalParams.Filter;
  dataSource: DataTableDataSource;
}

export interface TableViewParamsChangeEvent
{
  paginator?: Ngface.Paginator;
  sorter: Ngface.Sorter;
  filterer?: Ngface.Filterer;
}

export interface ActionClickEvent
{
  row: Ngface.Row;
  actionId: string;
}

export interface TableValueSetSearchEvent
{
  column: string;
  searchEvent: ValueSetSearchEvent;
}


@Component({
  selector: 'ngface-data-table',
  templateUrl: './ngface-data-table.component.html',
  styleUrls: ['./ngface-data-table.component.scss']
})
export class NgfaceDataTableComponent implements OnChanges, AfterViewInit
{
  @Input()
  formData: Form;

  @Input()
  widgetId: string;

  @Input()
  heightPx: number = 300;

  @Output()
  tableReloadEvent: EventEmitter<TableReloadEvent> = new EventEmitter();

  @Output()
  rowClickEvent: EventEmitter<Ngface.Row> = new EventEmitter();

  @Output()
  actionClickEvent: EventEmitter<ActionClickEvent> = new EventEmitter();

  @Output()
  tableValueSetSearchEvent: EventEmitter<TableValueSetSearchEvent> = new EventEmitter();

  @Output()
  tableViewParamsChangeEvent: EventEmitter<TableViewParamsChangeEvent> = new EventEmitter();

  @ViewChild(MatPaginator) matPaginator!: MatPaginator;
  @ViewChild(MatSort) matSort!: MatSort;
  @ViewChild(MatTable) matTable!: MatTable<any>;
  dataSource: DataTableDataSource = new DataTableDataSource();

  activeFilterer?: Ngface.Filterer;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns: string[];

  constructor(@Inject(LOCALE_ID) public locale: string,
              private el: ElementRef)
  {
  }

  ngOnChanges(): void
  {
    this.displayedColumns = [];
    let data: Ngface.Table = this.getData();
    if (data.selectMode === 'CHECKBOX')
    {
      this.displayedColumns = ['___checkbox-column___'];
    }
    Object.keys(data.columns).forEach(c => this.displayedColumns.push(c));
    this.dataSource.setWidgetData(data);

    if (this.getSortColumn())
    {
      this.matSort.active = this.getSortColumn()!;
      this.matSort.direction = this.getSortDirection();
    }

    Object.values(data.data.filtererMap).forEach(filterer =>
    {
      if (filterer.active)
      {
        this.activeFilterer = filterer;
      }
    });

    this.setHeightScrollableArea();
  }


  private setHeightScrollableArea()
  {
    let scrollableArea: any | undefined = undefined;

    this.el.nativeElement.childNodes[0].childNodes.forEach((i: any) =>
    {
      let node: ChildNode = i;
      if (node.nodeName === 'NG-SCROLLBAR')
      {
        scrollableArea = node;
      }
    });

    if (scrollableArea)
    {
      scrollableArea.style.height = this.heightPx.toString() + 'px';
    }
  }

  ngAfterViewInit(): void
  {
    this.matTable.dataSource = this.dataSource;

    merge(this.matSort.sortChange, this.matPaginator.page)
      .pipe(
        tap(() => this.reloadTable(this.dataSource))
      )
      .subscribe();
  }

  reloadTable(dataSource: DataTableDataSource)
  {
    let paramsChangeEvent: TableViewParamsChangeEvent = {
      paginator: {
        pageIndex: this.matPaginator.pageIndex,
        pageSize: this.matPaginator.pageSize,
        length: this.matPaginator.length,
        pageSizeOptions: this.matPaginator.pageSizeOptions
      },
      sorter: {column: this.matSort.active, direction: this.mapDirection(this.matSort.direction)},
      filterer: this.activeFilterer ? {
        active: true,
        column: this.activeFilterer.column,
        valueSet: this.activeFilterer.valueSet,
        searchText: this.activeFilterer.searchText
      } : undefined
    };
    this.tableViewParamsChangeEvent.emit(paramsChangeEvent);

    let reloadEvent: TableReloadEvent = {
      page: {index: this.matPaginator.pageIndex, size: this.matPaginator.pageSize},
      sort: {column: this.matSort.active, direction: this.mapDirection(this.matSort.direction)},
      filter: this.activeFilterer ? {
        column: this.activeFilterer.column,
        valueSet: this.activeFilterer.valueSet.values.filter(v => v.selected).map(v => this.getText(v))
      } : undefined,
      dataSource: dataSource
    };
    // Fire this one a bit later, so that the viewParamsChange event can submit the changes first
    setTimeout(() => this.tableReloadEvent.emit(reloadEvent), 100);
  }

  mapDirection(direction: SortDirection): Ngface.Direction
  {
    switch (direction)
    {
      case 'asc':
        return 'ASC';
      case 'desc':
        return 'DESC';
      case '':
        return 'UNDEFINED';
    }
  }

  getText(v: Ngface.ValueSet.Item): DataRetrievalParams.Filter.Item
  {
    return v.text != '(Blanks)' ? {text: v.text} : {text: undefined};
  }

  getHeaderText(column: string): string
  {
    let headerText = this.getData().columns[column]?.text;
    return headerText ? headerText : column;
  }

  getCellText(row: Ngface.Row, column: string): string
  {
    let cell = row.cells[column];
    if (!cell.value)
    {
      return 'NULL';
    }
    if (cell.type === 'TextCell')
    {
      return cell.value;
    }
    if (cell.type === 'NumericCell')
    {
      let numericCell = <NumericCell> cell;
      let formattedNumber = numericCell.format.prefix ?? '' + NumericFormatter.getFormattedValueAsText(numericCell.value, numericCell.format.precision, this.locale);
      if (numericCell.format.suffix)
      {
        formattedNumber += ' ' + numericCell.format.suffix;
      }
      return formattedNumber;
    }
    return '';
  }


  getCellLabel(row: Ngface.Row, column: string)
  {
    let cell = row.cells[column];
    if (!cell.value)
    {
      return '';
    }

    return cell.label;
  }


  getData(): Ngface.Table
  {
    let widget = this.formData?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'Table')
    {
      return {
        type: 'Table',
        columns: {},
        rows: [],
        data: {
          type: 'Table.Data',
          paginator: {pageIndex: 0, pageSize: 5, length: 0, pageSizeOptions: []},
          sorter: {column: '', direction: 'UNDEFINED'},
          filtererMap: {}
        },
        label: '',
        enabled: false,
        id: '',
        hint: '',
        selectMode: 'NONE',
        notification: '',
      };
    }
    return <Ngface.Table> this.formData.widgets[this.widgetId];
  }

  getPaginator(): Ngface.Paginator
  {
    if (!this.dataSource.paginator)
    {
      return {pageIndex: 0, pageSize: 5, length: 0, pageSizeOptions: []};
    }

    return this.dataSource.paginator;
  }

  isColumnSortable(column: string): boolean
  {
    let sortable = this.getData().columns[column]?.sortable;
    return sortable != undefined ? sortable : false;
  }

  getColumnSorter(column: string): Ngface.Sorter | undefined
  {
    let sorter = this.getData().data.sorter;
    return (sorter?.column === column) ? sorter : undefined;
  }

  isColumnFilterable(column: string): boolean
  {
    let filterable = !!this.getData().data.filtererMap[column];
    return filterable ?? false;
  }

  getColumnFilterer(column: string): Ngface.Filterer | undefined
  {
    if (this.activeFilterer?.column === column)
    {
      return this.activeFilterer;
    }

    let filterer = this.getData().data.filtererMap[column];
    if (filterer)
    {
      filterer.active = false;
    }
    return filterer;
  }

  getThClass(column: string): string | null
  {
    if (column === '___checkbox-column___')
    {
      return 'size-xsmall';
    }

    switch (this.getData().columns[column]?.size)
    {
      case 'AUTO':
        return 'size-auto';
      case 'XS':
        return 'size-xsmall';
      case 'S':
        return 'size-small';
      case 'M':
        return 'size-medium';
      case 'L':
        return 'size-large';
      case 'XL':
        return 'size-xlarge';
      case 'TIMESTAMP':
        return 'size-timestamp';
      case 'NUMBER':
        return 'size-number';
    }

    return null;
  }

  getTdClass(row: Ngface.Row, column: string): string | null
  {
    let cell = row.cells[column];
    if (!cell.value)
    {
      return 'cellvalue-null';
    }

    switch (this.getData().columns[column]?.textAlign)
    {
      case 'LEFT':
        return 'align-left';
      case 'CENTER':
        return 'align-center';
      case 'RIGHT':
        return 'align-right';
    }

    return null;
  }

  getOptionalClasses(): string
  {
    switch (this.getData().selectMode)
    {
      case 'NONE':
        return '';
      case 'SINGLE':
      case 'MULTI':
      case 'CHECKBOX':
        return 'ngface-data-table-selectable';
    }

    return '';
  }

  onRowClick(row: Ngface.Row)
  {
    if (this.getData().selectMode === 'SINGLE')
    {
      this.getData().rows.forEach(r => r.selected = false);
      row.selected = true;
    }
    if (this.getData().selectMode === 'MULTI' || this.getData().selectMode === 'CHECKBOX')
    {
      row.selected = !row.selected;
    }

    this.rowClickEvent.emit(row);
  }

  getRowClasses(row: Ngface.Row): string
  {
    if (row.selected)
    {
      return 'ngface-row-selected';
    }

    return '';
  }

  masterToggle($event: MatCheckboxChange)
  {
    this.dataSource.getRows().forEach(r => r.selected = $event.checked);
  }

  isChecked(row: Ngface.Row): boolean
  {
    return row.selected;
  }

  isAnySelected(): boolean
  {
    return !!this.dataSource.getRows().find(r => !!r.selected);
  }

  isAllSelected()
  {
    return this.dataSource.getRows().filter(r => !r.selected).length === 0;
  }

  actionClick(row: Ngface.Row, actionId: string)
  {
    this.actionClickEvent.emit({row: row, actionId: actionId});
  }

  getActions(row: Ngface.Row, column: string): Ngface.Action[] | null
  {
    if (row.cells[column].type === 'ActionCell')
    {
      return (<ActionCell> row.cells[column]).value;
    }
    return null;
  }

  getActionClass(action: Ngface.Action): string
  {
    if (action.enabled)
    {
      return 'ngface-mat-icon-enabled';
    }

    return 'ngface-mat-icon-disabled';
  }


  onValueSetSearch(column: string, $event: ValueSetSearchEvent)
  {
    this.tableValueSetSearchEvent.emit({column: column, searchEvent: $event});
  }

  onFiltererChange($event: Ngface.Filterer)
  {
    this.activeFilterer = $event;
    this.reloadTable(this.dataSource);
  }

  getNotification(): string
  {
    return this.getData().notification ?? '';
  }

  getSortColumn(): string | null
  {
    let sorter = this.getData().data.sorter;
    return sorter ? sorter.column : null;
  }

  getSortDirection(): SortDirection
  {
    let sorter = this.getData().data.sorter;
    switch (sorter?.direction)
    {
      case 'ASC':
        return 'asc';
      case 'DESC':
        return 'desc';
      default:
      case 'UNDEFINED':
        return '';
    }
  }
}

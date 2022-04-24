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

import {AfterViewInit, Component, EventEmitter, Inject, Input, LOCALE_ID, OnChanges, Output, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {DataTableDataSource} from './data-table-datasource';
import {DemoService} from '../../services/demo.service';
import {TypeModels} from '../../dto-models';
import {tap} from 'rxjs/operators';
import {merge} from 'rxjs';
import Form = TypeModels.Form;
import {MatCheckboxChange} from '@angular/material/checkbox';
import ActionCell = TypeModels.ActionCell;
import NumericCell = TypeModels.NumericCell;
import {formatNumber} from '@angular/common';
import {NumericFormatter} from '../numeric-formatter';

export interface TableReloadEvent
{
  pageIndex: number;
  pageSize: number;
  sortColumn: string;
  sortDirection: string;
  dataSource: DataTableDataSource;
}

export interface ActionClickEvent
{
  row: TypeModels.Row;
  actionId: string;
}

@Component({
  selector: 'ngface-data-table',
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.scss']
})
export class DataTableComponent implements OnChanges, AfterViewInit
{
  @Input()
  formData: Form;

  @Input()
  widgetId: string;

  @Output()
  tableReloadEvent: EventEmitter<TableReloadEvent> = new EventEmitter();

  @Output()
  rowClickEvent: EventEmitter<TypeModels.Row> = new EventEmitter();

  @Output()
  actionClickEvent: EventEmitter<ActionClickEvent> = new EventEmitter();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<any>;
  dataSource: DataTableDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns: string[];

  constructor(private demoService: DemoService, @Inject(LOCALE_ID) public locale: string)
  {
    this.dataSource = new DataTableDataSource(demoService);
  }

  ngOnChanges(): void
  {
    this.displayedColumns = [];
    if (this.getData().selectMode === 'CHECKBOX')
    {
      this.displayedColumns = ['___checkbox-column___'];
    }
    Object.keys(this.getData().columns).forEach(c => this.displayedColumns.push(c));
    this.dataSource.setWidgetData(this.getData());
  }


  ngAfterViewInit(): void
  {
    this.table.dataSource = this.dataSource;

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        tap(() => this.onPaginatorChanged(this.dataSource))
      )
      .subscribe();
  }

  onPaginatorChanged(dataSource: DataTableDataSource)
  {
    let event: TableReloadEvent = {
      pageIndex: this.paginator.pageIndex,
      pageSize: this.paginator.pageSize,
      sortColumn: this.sort.active,
      sortDirection: this.sort.direction,
      dataSource: dataSource
    };
    this.tableReloadEvent.emit(event);
  }

  getHeaderText(column: string): string
  {
    let headerText = this.getData().columns[column]?.text;
    return headerText ? headerText : column;
  }

  getCellText(row: TypeModels.Row, column: string): string
  {
    let cell = row.cells[column];
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


  getData(): TypeModels.Table
  {
    let widget = this.formData?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'Table')
    {
      return {
        type: 'Table',
        columns: {},
        rows: [],
        paginator: {pageSize: 5, length: 0, pageSizeOptions: []},
        data: {type: 'TextInput.Data'},
        label: 'undefined label',
        enabled: false,
        id: '',
        hint: '',
        selectMode: 'NONE'
      };
    }
    return <TypeModels.Table> this.formData.widgets[this.widgetId];
  }

  getPaginator(): TypeModels.Paginator
  {
    if (!this.getData().paginator)
    {
      return {pageSize: 5, length: 0, pageSizeOptions: []};
    }

    return this.getData().paginator;
  }

  isColumnSortable(column: string): boolean
  {
    let sortable = this.getData().columns[column]?.sortable;
    return sortable != undefined ? sortable : false;
  }

  getThClass(column: string): string | null
  {
    if (column === '___checkbox-column___')
    {
      return 'size-xsmall';
    }

    switch (this.getData().columns[column]?.size)
    {
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
    }

    return null;
  }

  getTdClass(column: string): string | null
  {
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

  onRowClick(row: TypeModels.Row)
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

  getRowClasses(row: TypeModels.Row): string
  {
    if (row.selected)
    {
      return 'ngface-row-selected';
    }

    return '';
  }

  masterToggle($event: MatCheckboxChange)
  {
    this.getData().rows.forEach(r => r.selected = $event.checked);
  }

  isChecked(row: TypeModels.Row): boolean
  {
    return row.selected;
  }

  isAnySelected(): boolean
  {
    return !!this.getData().rows.find(r => !!r.selected);
  }

  isAllSelected()
  {
    return this.getData().rows.filter(r => !r.selected).length === 0;
  }

  actionClick(row: TypeModels.Row, actionId: string)
  {
    this.actionClickEvent.emit({row: row, actionId: actionId});
  }

  getActions(row: TypeModels.Row, column: string): TypeModels.Action[] | null
  {
    if (row.cells[column].type === 'ActionCell')
    {
      return (<ActionCell> row.cells[column]).value;
    }
    return null;
  }

  getActionClass(action: TypeModels.Action): string
  {
    if (action.enabled)
    {
      return 'ngface-mat-icon-enabled';
    }

    return 'ngface-mat-icon-disabled';
  }
}

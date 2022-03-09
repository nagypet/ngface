import {AfterViewInit, Component, EventEmitter, Input, OnChanges, Output, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {DataTableDataSource} from './data-table-datasource';
import {DemoService} from '../../services/demo.service';
import {TypeModels} from '../../dto-models';
import {tap} from 'rxjs/operators';
import {merge} from 'rxjs';
import Form = TypeModels.Form;

export interface TableReloadEvent
{
  pageIndex: number;
  pageSize: number;
  sortColumn: string;
  sortDirection: string;
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

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<any>;
  dataSource: DataTableDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns: string[] = [];

  constructor(private demoService: DemoService)
  {
    this.dataSource = new DataTableDataSource(demoService);
  }

  ngOnChanges(): void
  {
    this.displayedColumns = Object.keys(this.getData().columns)
    this.dataSource.setWidgetData(this.getData());
  }


  ngAfterViewInit(): void
  {
    this.table.dataSource = this.dataSource;

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        tap(() => this.onPaginatorChanged())
      )
      .subscribe();
  }

  onPaginatorChanged()
  {
    let event: TableReloadEvent = {pageIndex: this.paginator.pageIndex, pageSize: this.paginator.pageSize, sortColumn: this.sort.active, sortDirection: this.sort.direction};
    this.tableReloadEvent.emit(event);
  }

  getHeaderText(column: string): string
  {
    let headerText = this.getData().columns[column]?.text;
    return headerText ? headerText : column;
  }

  getCellText(row: TypeModels.Row, column: string): string
  {
    return row.cells[column];
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
    switch (this.getData().columns[column].size)
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

  getOptionalClasses(): string
  {
    switch (this.getData().selectMode)
    {
      case 'NONE':
        return '';
      case 'SINGLE':
      case 'MULTI':
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
    if (this.getData().selectMode === 'MULTI')
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
}

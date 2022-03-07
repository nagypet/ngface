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
    this.displayedColumns = [];
    this.getData().columns.forEach(col => this.displayedColumns.push(col.id));
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
    let headerText = this.getData().columns.find(i => (i.id === column))?.text;
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
        columns: [],
        rows: [],
        paginator: {pageSize: 5, length: 0, pageSizeOptions: []},
        data: {type: 'TextInput.Data'},
        label: 'undefined label',
        enabled: false,
        id: '',
        hint: ''
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
    let sortable = this.getData().columns.find(i => (i.id === column))?.sortable;
    return sortable != undefined ? sortable : false;
  }
}

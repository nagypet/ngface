import {AfterViewInit, Component, Input, OnChanges, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {DataTableDataSource, DataTableItem} from './data-table-datasource';
import {DemoService} from '../../services/demo.service';
import {tap} from 'rxjs/operators';
import {TypeModels} from '../../dto-models';
import Form = TypeModels.Form;

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

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<DataTableItem>;
  dataSource: DataTableDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns: string[] = ['id', 'name', 'weight', 'symbol'];

  constructor(private demoService: DemoService)
  {
    this.dataSource = new DataTableDataSource(demoService);
  }

  ngOnChanges(): void
  {
    this.dataSource.loadData(0, this.getPaginator().pageSize);
  }

  ngAfterViewInit(): void
  {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;

    this.paginator.page
      .pipe(
        tap(() => this.dataSource.loadData(this.paginator.pageIndex, this.paginator.pageSize))
      )
      .subscribe();
  }

  getHeaderText(column: string): string
  {
    let headerText = this.getData().data?.columns.find(i => (i.id === column))?.text;
    return headerText ? headerText : column;
  }

  getCellText(row: any, column: string): string
  {
    //console.log(row, column);
    return row[column];
  }


  getData(): TypeModels.Table
  {
    let widget = this.formData?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'Table')
    {
      return {
        type: 'Table',
        data: {type: 'TextInput.Data', columns: [], rows: [], paginator: {pageSize: 10, length: 0}},
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
    if (!this.getData()?.data?.paginator)
    {
      return {pageSize: 10, length: 0};
    }

    return this.getData()?.data?.paginator;
  }

  onPaginatorClick()
  {
    console.log('onPaginatorClick');
  }
}

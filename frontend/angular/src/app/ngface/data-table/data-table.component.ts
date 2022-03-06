import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {DataTableDataSource, DataTableItem} from './data-table-datasource';

@Component({
  selector: 'ngface-data-table',
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.scss']
})
export class DataTableComponent implements AfterViewInit
{
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<DataTableItem>;
  dataSource: DataTableDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns: string[] = ['id', 'name', 'weight', 'symbol'];

  constructor()
  {
    this.dataSource = new DataTableDataSource();
  }

  ngAfterViewInit(): void
  {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }

  getHeaderText(column: string): string
  {
    return column;
  }

  getCellText(row: any, column: string): string
  {
    console.log(row, column);
    return row[column];
  }
}

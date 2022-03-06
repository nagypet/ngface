import {DataSource} from '@angular/cdk/collections';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {catchError, finalize} from 'rxjs/operators';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {DemoService} from '../../services/demo.service';
import {TypeModels} from '../../dto-models';


// TODO: Replace this with your own data model type
export interface DataTableItem
{
  id: string;
  name: string;
  weight: string;
  symbol: string;
}


/**
 * Data source for the DataTable view. This class should
 * encapsulate all logic for fetching and manipulating the displayed data
 * (including sorting, pagination, and filtering).
 */
export class DataTableDataSource extends DataSource<DataTableItem>
{
  //data: DataTableItem[] = EXAMPLE_DATA;
  paginator: MatPaginator | undefined;
  sort: MatSort | undefined;

  private dataSubject = new BehaviorSubject<DataTableItem[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);

  constructor(private demoService: DemoService)
  {
    super();
  }

  /**
   * Connect this data source to the table. The table will only update when
   * the returned stream emits new items.
   * @returns A stream of the items to be rendered.
   */
  connect(): Observable<DataTableItem[]>
  {
    return this.dataSubject.asObservable();
    // if (this.paginator && this.sort) {
    //   // Combine everything that affects the rendered data into one update
    //   // stream for the data-table to consume.
    //   return merge(observableOf(this.data), this.paginator.page, this.sort.sortChange)
    //     .pipe(map(() => {
    //       return this.getPagedData(this.getSortedData([...this.data ]));
    //     }));
    // } else {
    //   throw Error('Please set the paginator and sort on the data source before connecting.');
    // }
  }

  /**
   *  Called when the table is being destroyed. Use this function, to clean up
   * any open connections or free any held resources that were set up during connect.
   */
  disconnect(): void
  {
    this.dataSubject.complete();
  }

  /**
   * Paginate the data (client-side). If you're using server-side pagination,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getPagedData(data: DataTableItem[]): DataTableItem[]
  {
    if (this.paginator)
    {
      const startIndex = this.paginator.pageIndex * this.paginator.pageSize;
      return data.splice(startIndex, this.paginator.pageSize);
    } else
    {
      return data;
    }
  }

  /**
   * Sort the data (client-side). If you're using server-side sorting,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getSortedData(data: DataTableItem[]): DataTableItem[]
  {
    if (!this.sort || !this.sort.active || this.sort.direction === '')
    {
      return data;
    }

    return data.sort((a, b) =>
    {
      const isAsc = this.sort?.direction === 'asc';
      switch (this.sort?.active)
      {
        case 'name':
          return compare(a.name, b.name, isAsc);
        case 'id':
          return compare(+a.id, +b.id, isAsc);
        default:
          return 0;
      }
    });
  }


  loadData(pageNumber: number, pageSize: number)
  {

    this.loadingSubject.next(true);

    this.demoService.getDemoForm(pageNumber, pageSize).pipe(
      catchError(() => of([])),
      finalize(() => this.loadingSubject.next(false))
    )
      .subscribe(data =>
      {
        console.log(data);
        let tableData: TypeModels.Table = <TypeModels.Table>(data?.widgets['table']);
        let dataTableItems: DataTableItem[] = [];
        if (tableData)
        {
          tableData.data?.rows?.forEach(row => dataTableItems.push({id: row.cells[0], name: row.cells[1], weight: row.cells[2], symbol: row.cells[3]}))
        }
        this.dataSubject.next(dataTableItems);
      });
  }

}

/** Simple sort comparator for example ID/Name columns (for client-side sorting). */
function compare(a: string | number, b: string | number, isAsc: boolean): number
{
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}

import {DataSource} from '@angular/cdk/collections';
import {BehaviorSubject, Observable} from 'rxjs';
import {DemoService} from '../../services/demo.service';
import {TypeModels} from '../../dto-models';


/**
 * Data source for the DataTable view. This class should
 * encapsulate all logic for fetching and manipulating the displayed data
 * (including sorting, pagination, and filtering).
 */
export class DataTableDataSource extends DataSource<TypeModels.Row>
{
  private dataSubject = new BehaviorSubject<TypeModels.Row[]>([]);
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
  connect(): Observable<TypeModels.Row[]>
  {
    return this.dataSubject.asObservable();
  }

  /**
   *  Called when the table is being destroyed. Use this function, to clean up
   * any open connections or free any held resources that were set up during connect.
   */
  disconnect(): void
  {
    this.dataSubject.complete();
  }

  setWidgetData(tableData: TypeModels.Table)
  {
    if (tableData)
    {
      this.dataSubject.next(tableData.data?.rows);
    }
  }
}

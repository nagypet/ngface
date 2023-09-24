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

import {Component, OnInit} from '@angular/core';
import {DemoService} from '../services/demo.service';
import {MatDialog} from '@angular/material/dialog';
import {DemoDialog1Component} from '../demo-dialog1/demo-dialog1.component';
import {
  ActionClickEvent,
  TableReloadEvent,
  TableValueSetSearchEvent,
  TableViewParamsChangeEvent,
} from '../../../../ngface/src/lib/ngface-data-table/ngface-data-table.component';
import {Ngface} from '../../../../ngface/src/lib/ngface-models';
import {FormBaseComponent} from '../../../../ngface/src/lib/form-base.component';


@Component({
  selector: 'app-demo-form1',
  templateUrl: './demo-form1.component.html',
  styleUrls: ['./demo-form1.component.scss']
})
export class DemoForm1Component extends FormBaseComponent implements OnInit
{
  constructor(private demoService: DemoService, public dialog: MatDialog)
  {
    super();
  }

  ngOnInit(): void
  {
    this.demoService.getDemoForm({page: null, sort: null, filters: null}).subscribe(data =>
    {
      console.log(data);
      this.formData = data;
      // this.enableButtons();
    });
  }


  enableButtons(): void
  {
    const button: Ngface.Button = this.formData?.widgets['button-details'] as Ngface.Button;
    button.enabled = false;

    const selectedRow = this.getSingleSelectTableSelectedRow();
    if (selectedRow)
    {
      button.enabled = true;
    }
  }


  private getSingleSelectTableSelectedRow(): Ngface.Row | undefined
  {
    const table: Ngface.Table = this.formData?.widgets['table-singleselect'] as Ngface.Table;
    return table.rows.find(r => r.selected);
  }

  onOkClick(): void
  {
    this.formGroup.markAllAsTouched();
    if (!this.formGroup.valid)
    {
      console.warn('Data is invalid!');
    }
    else
    {
      const submitData = this.getSubmitData();
      console.log(submitData);

      this.demoService.submitDemoForm({id: '', widgetDataMap: submitData}).subscribe(
        () => console.log('sumbitted'),
        error => console.log(error));
    }
  }


  onTableReload($event: TableReloadEvent): void
  {
    this.demoService.getDemoForm({
      page: $event.page,
      sort: $event.sort,
      filters: $event.filters
    }).subscribe(data =>
    {
      console.log(data);
      $event.dataSource.setWidgetData(data.widgets['table-multiselect'] as Ngface.Table);
      if (this.formData)
      {
        this.formData.widgets['table-multiselect'] = data.widgets['table-multiselect'];
      }
    });
  }


  onDetails(): void
  {
    this.doEditAction(this.getSingleSelectTableSelectedRow());
  }

  onSingleSelectTableRowClick(): void
  {
    this.enableButtons();
  }

  onTableActionClick($event: ActionClickEvent): void
  {
    if ($event.actionId === 'edit')
    {
      this.doEditAction($event.row);
    }
  }

  private doEditAction(row: Ngface.Row | undefined): void
  {
    if (!row)
    {
      return;
    }

    // Reading dialog data from the backend
    this.demoService.getTableDetailsForm(row.id).subscribe(dialogData =>
    {
      console.log(dialogData);
      // Open dialog
      const dialogRef = this.dialog.open(DemoDialog1Component, {
        data: dialogData,
        backdropClass: 'ngface-modal-dialog-backdrop'
      });

      // Subscribe to afterClosed
      dialogRef.afterClosed().subscribe(result =>
      {
        if (result)
        {
          // Submitting new data to the backend
          this.demoService.submitTableDetailsForm({id: row.id, widgetDataMap: result}).subscribe(
            () =>
            {
              console.log('sumbitted');
              // reload table content
              this.demoService.getDemoFormTableRow(row.id).subscribe(data =>
              {
                console.log(data);
                // @ts-ignore
                row.cells = data.widgets['table-multiselect'].rows[0].cells;
              });
            },
            error => console.log(error));
        }
      });
    });
  }


  onTableValueSetSearch($event: TableValueSetSearchEvent): void
  {
    console.log($event);

    // Ask the backend for updated set of criteria based on 'column' and 'searchText'
    this.demoService.getColumnFilterer($event.column, $event.searchEvent.searchText).subscribe(filterer =>
    {
      console.log(filterer);
      $event.searchEvent.valueSetProvider.setValueSet(filterer.valueSet);
    });
  }

  onTableViewParamsChange($event: TableViewParamsChangeEvent): void
  {
    console.log($event);

    const widgetData: Ngface.Table.Data = {
      type: 'Table.Data',
      paginator: $event.paginator,
      sorter: $event.sorter,
      filtererMap: $event.filtererMap
    };
    this.demoService.submitDemoForm({id: '', widgetDataMap: {['table-multiselect']: widgetData}}).subscribe(
      () => console.log(widgetData));
  }
}

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
import {FormBaseComponent} from '../../../../ngface/src/lib/form-base.component';
import {Ngface} from '../../../../ngface/src/lib/ngface-models';


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

  ngOnInit()
  {
    this.demoService.getDemoForm({page: undefined, sort: undefined, filters: undefined}).subscribe(data =>
    {
      console.log(data);
      this.formData = data;
      //this.enableButtons();
    });
  }


  enableButtons()
  {
    const button: Ngface.Button = <Ngface.Button> this.formData?.widgets['button-details'];
    button.enabled = false;

    var selectedRow = this.getSingleSelectTableSelectedRow();
    if (selectedRow)
    {
      button.enabled = true;
    }
  }


  private getSingleSelectTableSelectedRow(): Ngface.Row | undefined
  {
    const table: Ngface.Table = <Ngface.Table> this.formData?.widgets['table-singleselect'];
    return table.rows.find(r => r.selected);
  }

  onOkClick()
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


  onTableReload($event: TableReloadEvent)
  {
    this.demoService.getDemoForm({
      page: $event.page,
      sort: $event.sort,
      filters: $event.filter ? [$event.filter] : undefined
    }).subscribe(data =>
    {
      console.log(data);
      $event.dataSource.setWidgetData(<Ngface.Table> data.widgets['table-multiselect']);
      this.formData.widgets['table-multiselect'] = data.widgets['table-multiselect'];
    });
  }


  onDetails()
  {
    this.doEditAction(this.getSingleSelectTableSelectedRow());
  }

  onSingleSelectTableRowClick()
  {
    this.enableButtons();
  }

  onTableActionClick($event: ActionClickEvent)
  {
    if ($event.actionId === 'edit')
    {
      this.doEditAction($event.row);
    }
  }

  private doEditAction(row: Ngface.Row | undefined)
  {
    if (!row)
    {
      return;
    }

    // Reading dialog data from the baclend
    this.demoService.getTableDetailsForm(row.id).subscribe(dialogData =>
    {
      console.log(dialogData);
      // Open dialog
      const dialogRef = this.dialog.open(DemoDialog1Component, {
        width: '590px',
        data: dialogData,
        backdropClass: 'ngface-modal-dialog-backdrop'
      });

      // Subscribe to afterClosed
      dialogRef.afterClosed().subscribe(result =>
      {
        console.log(result);
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


  onTableValueSetSearch($event: TableValueSetSearchEvent)
  {
    console.log($event);

    // TODO: this function is only called in case of remote filterers, where actual value sets will be asked from the backend.
    // In this demo each filterer is local.
    // Ask the backend for updated set of criteria based on 'column' and 'searchText'
    // this.demoService.getColumnFilterer($event.column, $event.searchEvent.searchText).subscribe(filterer =>
    // {
    //   console.log(filterer);
    //   $event.searchEvent.valueSetProvider.setValueSet(filterer.valueSet);
    // });
  }

  onTableViewParamsChange($event: TableViewParamsChangeEvent)
  {
    console.log($event);

    let widgetData: Ngface.Table.Data = {
      type: 'Table.Data',
      paginator: $event.paginator,
      sorter: $event.sorter,
      filtererMap: $event.filterer ? {[$event.filterer?.column]: $event.filterer} : {}
    };
    this.demoService.submitDemoForm({id: '', widgetDataMap: {['table-multiselect']: widgetData}}).subscribe(
      () => console.log(widgetData));
  }
}

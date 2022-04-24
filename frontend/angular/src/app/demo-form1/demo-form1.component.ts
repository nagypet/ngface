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
import {FormBaseComponent} from '../ngface/form-base.component';
import {ActionClickEvent, TableReloadEvent, TableSearchEvent} from '../ngface/data-table/data-table.component';
import {TypeModels} from '../dto-models';
import {MatDialog} from '@angular/material/dialog';
import {DemoDialog1Component} from '../demo-dialog1/demo-dialog1.component';
import {SearchItem} from '../ngface/data-table/excel-filter/excel-filter.component';


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
    this.demoService.getDemoForm().subscribe(data =>
    {
      console.log(data);
      this.formData = data;
      //this.enableButtons();
    });
  }


  enableButtons()
  {
    const button: TypeModels.Button = <TypeModels.Button> this.formData?.widgets['button-details'];
    button.enabled = false;

    var selectedRow = this.getSingleSelectTableSelectedRow();
    if (selectedRow)
    {
      button.enabled = true;
    }
  }


  private getSingleSelectTableSelectedRow(): TypeModels.Row | undefined
  {
    const table: TypeModels.Table = <TypeModels.Table> this.formData?.widgets['table-singleselect'];
    return table.rows.find(r => r.selected);
  }

  onOkClick()
  {
    this.formGroup.markAllAsTouched();
    if (!this.formGroup.valid)
    {
      console.warn('Data is invalid!');
    } else
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
    this.demoService.getDemoForm($event.pageIndex, $event.pageSize, $event.sortColumn, $event.sortDirection).subscribe(data =>
    {
      console.log(data);
      $event.dataSource.setWidgetData(<TypeModels.Table> data.widgets['table-multiselect']);
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

  private doEditAction(row: TypeModels.Row | undefined)
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
              this.demoService.getDemoForm(undefined, undefined, undefined, undefined, row.id).subscribe(data =>
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


  onTableSearchEvent($event: TableSearchEvent)
  {
    console.log($event);

    let c: SearchItem[] = [];
    for (let i = 0; i < $event.searchEvent.searchText.length; i++)
    {
      c.push({text: 'alma', selected: false});
    }
    $event.searchEvent.searchResultProvider.choises = c;
  }
}

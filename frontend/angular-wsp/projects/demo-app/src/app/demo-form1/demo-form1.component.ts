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
import {DemoFormService} from '../services/demo-form.service';
import {MatDialog} from '@angular/material/dialog';
import {DemoDialog1Component} from '../demo-dialog1/demo-dialog1.component';
import {
  ActionClickEvent,
  NgfaceDataTableComponent,
  TableMasterToggleEvent,
  TableReloadEvent,
  TableValueSetSearchEvent,
  TableViewParamsChangeEvent
} from '../../../../ngface/src/lib/widgets/ngface-data-table/ngface-data-table.component';
import {FormBaseComponent} from '../../../../ngface/src/lib/form/form-base.component';
import {DemoFormTableService} from '../services/demo-form-table.service';
import {TableDetailsService} from '../services/table-details.service';
import {Ngface} from '../../../../ngface/src/lib/ngface-models';
import {NgIf} from '@angular/common';
import {NgfaceButtonComponent} from '../../../../ngface/src/lib/widgets/ngface-button/ngface-button.component';
import {NgfaceSelectComponent} from '../../../../ngface/src/lib/widgets/ngface-select/ngface-select.component';
import {NgfaceDateRangeInputComponent} from '../../../../ngface/src/lib/widgets/ngface-date-range-input/ngface-date-range-input.component';
import {NgfaceDateInputComponent} from '../../../../ngface/src/lib/widgets/ngface-date-input/ngface-date-input.component';
import {NgfaceNumericInputComponent} from '../../../../ngface/src/lib/widgets/ngface-numeric-input/ngface-numeric-input.component';
import {NgfaceTextInputComponent} from '../../../../ngface/src/lib/widgets/ngface-text-input/ngface-text-input.component';
import {NgfaceFormComponent} from '../../../../ngface/src/lib/form/ngface-form/ngface-form.component';

// tslint:disable-next-line:no-namespace
export namespace DemoForm1Component
{
  export type Actions = 'SELECT_ALL' | 'SELECT_NONE';
}


@Component({
  selector: 'app-demo-form1',
  templateUrl: './demo-form1.component.html',
  styleUrls: ['./demo-form1.component.scss'],
  standalone: true,
  imports: [NgfaceFormComponent, NgfaceTextInputComponent, NgfaceNumericInputComponent, NgfaceDateInputComponent, NgfaceDateRangeInputComponent, NgfaceSelectComponent, NgfaceButtonComponent, NgfaceDataTableComponent, NgIf]
})
export class DemoForm1Component extends FormBaseComponent implements OnInit
{
  constructor(
    private demoFormService: DemoFormService,
    private demoFormTableService: DemoFormTableService,
    private tableDetailsService: TableDetailsService,
    public dialog: MatDialog)
  {
    super();
  }

  ngOnInit(): void
  {
    this.demoFormService.getDemoForm().subscribe(form =>
    {
      console.log(form);
      this.demoFormTableService.getDemoFormTable().subscribe(table =>
      {
        console.log(table);
        if (form)
        {
          // Merge table.widgets into forms.widgets
          Object.keys(table.widgets).forEach(widgetId => form.widgets[widgetId] = table.widgets[widgetId]);
          this.formData = form;
        }
      });
    });
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

      this.demoFormService.submitDemoForm({id: '', widgetDataMap: submitData}).subscribe(
        () => console.log('sumbitted'),
        error => console.log(error));
    }
  }


  onTableReload($event: TableReloadEvent): void
  {
    this.reloadTable({page: $event.page, sort: $event.sort, filters: $event.filters});
  }


  private reloadTable(searchRequest?: Ngface.DataRetrievalParams): void
  {
    this.demoFormTableService.getDemoFormTable(searchRequest).subscribe(table =>
    {
      console.log(table);
      // This is required to trigger change detection
      const form = (JSON.parse(JSON.stringify(this.formData)));
      // Merge table.widgets into forms.widgets
      if (form)
      {
        Object.keys(table.widgets).forEach(widgetId =>
        {
          form.widgets[widgetId] = table.widgets[widgetId];
        });
        this.formData = form;
      }
    });
  }


  onTableActionClick($event: ActionClickEvent): void
  {
    if ($event.actionId === 'edit')
    {
      this.doEditAction($event.row);
    }
  }

  private doEditAction(row: Ngface.Row<number> | undefined): void
  {
    if (!row)
    {
      return;
    }

    const rowid: number = row.id;
    // Reading dialog data from the backend
    this.tableDetailsService.getTableDetailsForm(rowid).subscribe(dialogData =>
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
          this.tableDetailsService.submitTableDetailsForm({id: row.id.toString(), widgetDataMap: result}).subscribe(
            () =>
            {
              console.log('sumbitted');
              // reload table content
              this.demoFormTableService.getDemoFormTableRow(row.id).subscribe(data =>
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
    this.demoFormTableService.getColumnFilterer($event.column, $event.searchEvent.searchText).subscribe(filterer =>
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
    this.demoFormTableService.submitDemoFormTable({id: '', widgetDataMap: {['table-multiselect']: widgetData}}).subscribe(
      () => console.log(widgetData));
  }

  onReloadClick(): void
  {
    this.demoFormService.reloadTableData().subscribe(
      () =>
      {
        console.log('Table data reloaded!');
        window.location.reload();
      });
  }

  onRowClick($event: Ngface.Row<number>): void
  {
    this.demoFormTableService.onRowSelect(
      {selectMode: 'SINGLE', rows: [{id: $event.id, selected: $event.selected}]})
      .subscribe(() =>
      {
        this.reloadTable();
      });
  }

  onActionButton(action: DemoForm1Component.Actions): void
  {
    if (action === 'SELECT_ALL')
    {
      this.demoFormTableService.onRowSelect(
        {selectMode: 'ALL_CHECKED', rows: []})
        .subscribe(() =>
        {
          this.reloadTable();
        });
    }

    if (action === 'SELECT_NONE')
    {
      this.demoFormTableService.onRowSelect(
        {selectMode: 'ALL_UNCHECKED', rows: []})
        .subscribe(() =>
        {
          this.reloadTable();
        });
    }

  }

  onMasterToggle($event: TableMasterToggleEvent): void
  {
    const table: Ngface.Table<number> = this.formData?.widgets['table-multiselect'] as Ngface.Table<number>;
    if (!table || !table.rows)
    {
      return;
    }
    this.demoFormTableService.onRowSelect(
      {selectMode: 'SINGLE', rows: table.rows.map(i => ({id: i.id, selected: $event.checked}))})
      .subscribe(() =>
      {
        this.reloadTable();
      });
  }
}

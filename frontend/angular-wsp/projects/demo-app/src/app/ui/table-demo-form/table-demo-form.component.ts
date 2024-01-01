/*
 * Copyright 2020-2023 the original author or authors.
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
import {NgIf} from '@angular/common';
import {NgfaceButtonComponent} from '../../../../../ngface/src/lib/widgets/ngface-button/ngface-button.component';
import {
    ActionClickEvent,
    NgfaceDataTableComponent,
    TableMasterToggleEvent,
    TableReloadEvent,
    TableValueSetSearchEvent,
    TableViewParamsChangeEvent
} from '../../../../../ngface/src/lib/widgets/ngface-data-table/ngface-data-table.component';
import {NgfaceFormComponent} from '../../../../../ngface/src/lib/form/ngface-form/ngface-form.component';
import {FormBaseComponent} from '../../../../../ngface/src/lib/form/form-base.component';
import {TableDemoFormService} from '../../core/services/table-demo-form.service';
import {TableDetailsService} from '../../core/services/table-details.service';
import {MatDialog} from '@angular/material/dialog';
import {Ngface} from '../../../../../ngface/src/lib/ngface-models';
import {TableDetailsDialogComponent} from '../table-details-dialog/table-details-dialog.component';
import {WidgetDemoFormComponent} from '../widget-demo-form/widget-demo-form.component';
import {ResponsiveClassDirective} from '../../../../../ngface/src/lib/directives/responsive-class-directive';
import {DeviceTypeService} from '../../../../../ngface/src/lib/services/device-type.service';

@Component({
    selector: 'app-table-demo-form',
    templateUrl: './table-demo-form.component.html',
    styleUrls: ['./table-demo-form.component.scss'],
    imports: [
        NgIf,
        NgfaceButtonComponent,
        NgfaceDataTableComponent,
        NgfaceFormComponent,
        ResponsiveClassDirective
    ],
    standalone: true
})
export class TableDemoFormComponent extends FormBaseComponent implements OnInit
{

    constructor(
        private tableDemoFormService: TableDemoFormService,
        private tableDetailsService: TableDetailsService,
        public dialog: MatDialog,
        public deviceTypeService: DeviceTypeService)
    {
        super();
    }

    ngOnInit(): void
    {
        this.tableDemoFormService.getDemoFormTable().subscribe(form =>
        {
            console.log(form);
            this.formData = form;
        });
    }


    onTableReload($event: TableReloadEvent): void
    {
        this.reloadTable({page: $event.page, sort: $event.sort, filters: $event.filters});
    }


    private reloadTable(searchRequest?: Ngface.DataRetrievalParams): void
    {
        this.tableDemoFormService.getDemoFormTable(searchRequest).subscribe(table =>
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


    onTableActionClick($event: ActionClickEvent<number>): void
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
            const dialogRef = this.dialog.open(TableDetailsDialogComponent, {
                data: dialogData,
                backdropClass: 'ngface-modal-dialog-backdrop',
                minWidth: this.deviceTypeService.deviceType === 'Phone' ? '100%' : undefined
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
                            this.tableDemoFormService.getDemoFormTableRow(row.id).subscribe(data =>
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
        this.tableDemoFormService.getColumnFilterer($event.column, $event.searchEvent.searchText).subscribe(filterer =>
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
        this.tableDemoFormService.submitDemoFormTable({id: '', widgetDataMap: {['table-multiselect']: widgetData}}).subscribe(
            () => console.log(widgetData));
    }

    onReloadClick(): void
    {
        this.tableDemoFormService.reloadTableData().subscribe(
            () =>
            {
                console.log('Table data reloaded!');
                window.location.reload();
            });
    }

    onRowClick($event: Ngface.Row<number>): void
    {
        this.tableDemoFormService.onRowSelect(
            {selectMode: 'SINGLE', rows: [{id: $event.id, selected: $event.selected}]})
            .subscribe(() =>
            {
                this.reloadTable();
            });
    }

    onActionButton(action: WidgetDemoFormComponent.Actions): void
    {
        if (action === 'SELECT_ALL')
        {
            this.tableDemoFormService.onRowSelect(
                {selectMode: 'ALL_CHECKED', rows: []})
                .subscribe(() =>
                {
                    this.reloadTable();
                });
        }

        if (action === 'SELECT_NONE')
        {
            this.tableDemoFormService.onRowSelect(
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
        this.tableDemoFormService.onRowSelect(
            {selectMode: 'SINGLE', rows: table.rows.map(i => ({id: i.id, selected: $event.checked}))})
            .subscribe(() =>
            {
                this.reloadTable();
            });
    }
}

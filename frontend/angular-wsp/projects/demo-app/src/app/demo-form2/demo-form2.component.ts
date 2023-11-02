import {Component, OnInit} from '@angular/core';
import {NgIf} from '@angular/common';
import {NgfaceButtonComponent} from '../../../../ngface/src/lib/widgets/ngface-button/ngface-button.component';
import {
    ActionClickEvent,
    NgfaceDataTableComponent,
    TableMasterToggleEvent,
    TableReloadEvent,
    TableValueSetSearchEvent,
    TableViewParamsChangeEvent
} from '../../../../ngface/src/lib/widgets/ngface-data-table/ngface-data-table.component';
import {NgfaceFormComponent} from '../../../../ngface/src/lib/form/ngface-form/ngface-form.component';
import {FormBaseComponent} from '../../../../ngface/src/lib/form/form-base.component';
import {TableDemoFormService} from '../services/table-demo-form.service';
import {TableDetailsService} from '../services/table-details.service';
import {MatDialog} from '@angular/material/dialog';
import {Ngface} from '../../../../ngface/src/lib/ngface-models';
import {DemoDialog1Component} from '../demo-dialog1/demo-dialog1.component';
import {DemoForm1Component} from '../demo-form1/demo-form1.component';

@Component({
    selector: 'app-demo-form2',
    templateUrl: './demo-form2.component.html',
    styleUrls: ['./demo-form2.component.scss'],
    imports: [
        NgIf,
        NgfaceButtonComponent,
        NgfaceDataTableComponent,
        NgfaceFormComponent
    ],
    standalone: true
})
export class DemoForm2Component extends FormBaseComponent implements OnInit
{

    constructor(
        private demoFormTableService: TableDemoFormService,
        private tableDetailsService: TableDetailsService,
        public dialog: MatDialog)
    {
        super();
    }

    ngOnInit(): void
    {
        this.demoFormTableService.getDemoFormTable().subscribe(form =>
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
        this.demoFormTableService.reloadTableData().subscribe(
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

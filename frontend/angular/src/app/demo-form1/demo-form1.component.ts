import {Component, OnInit} from '@angular/core';
import {DemoService} from '../services/demo.service';
import {FormBaseComponent} from '../ngface/form-base.component';
import {TableReloadEvent} from '../ngface/data-table/data-table.component';
import {TypeModels} from '../dto-models';
import {MatDialog} from '@angular/material/dialog';
import {DemoDialog1Component} from '../demo-dialog1/demo-dialog1.component';


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
      this.enableButtons();
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
      this.formData = data;
    });
  }


  onDetails()
  {
    let selectedRow = this.getSingleSelectTableSelectedRow();
    if (selectedRow)
    {
      console.log(selectedRow);
      this.demoService.getTableDetailsForm(selectedRow.id).subscribe(dialogData =>
      {
        console.log(dialogData);
        const dialogRef = this.dialog.open(DemoDialog1Component, {
          width: '590px',
          data: dialogData,
          backdropClass: 'ngface-modal-dialog-backdrop'
        });

        dialogRef.afterClosed().subscribe(result =>
        {
          console.log(result);
          if (result)
          {
            this.demoService.submitTableDetailsForm({id: selectedRow!.id, widgetDataMap: result}).subscribe(
              () => console.log('sumbitted'),
              error => console.log(error));

            // reload table content
            selectedRow!.cells['symbol'] = result['symbol'].value;
            selectedRow!.cells['weight'] = result['weight'].value;
          }
        });
      });
    }
  }

  onSingleSelectTableRowClick()
  {
    this.enableButtons();
  }
}

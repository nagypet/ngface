import {Component, OnInit} from '@angular/core';
import {DemoService} from '../services/demo.service';
import {FormBaseComponent} from '../ngface/form-base.component';
import {TableReloadEvent} from '../ngface/data-table/data-table.component';
import {TypeModels} from '../dto-models';

@Component({
  selector: 'app-demo-form1',
  templateUrl: './demo-form1.component.html',
  styleUrls: ['./demo-form1.component.scss']
})
export class DemoForm1Component extends FormBaseComponent implements OnInit
{

  constructor(private demoService: DemoService)
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

    var selectedRow = this.getSelectedRow();
    if (selectedRow)
    {
      button.enabled = true;
    }
  }


  private getSelectedRow(): TypeModels.Row | undefined
  {
    const table: TypeModels.Table = <TypeModels.Table> this.formData?.widgets['table-singleselect'];
    return table.rows.find(r => r.selected);
  }

  onOk()
  {
    this.formGroup.markAllAsTouched();
    if (!this.formGroup.valid)
    {
      console.warn('Data is invalid!');
    } else
    {
      const submitData = this.getSubmitData();
      console.log(submitData);

      this.demoService.submitDemoForm({widgetDataMap: submitData}).subscribe(
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
    var selectedRow = this.getSelectedRow();
    if (selectedRow)
    {
      console.log(selectedRow);
    }
  }

  onSingleSelectTableRowClick()
  {
    this.enableButtons();
  }
}

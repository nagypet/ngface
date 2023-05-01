// @ts-nocheck

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
// import {DemoDialog1Component} from '../demo-dialog1/demo-dialog1.component';
// import {
//   ActionClickEvent,
//   TableReloadEvent,
//   TableValueSetSearchEvent,
//   TableViewParamsChangeEvent,
// } from 'ngface/ngface-webcomp';
import {Ngface} from 'ngface/ngface-models';
import {FormBaseComponent} from '../form-base.component';


@Component({
  selector: 'app-demo-form1',
  templateUrl: './demo-form1.component.html',
  styleUrls: ['./demo-form1.component.scss']
})
export class DemoForm1Component extends FormBaseComponent implements OnInit
{
  myForm;

  constructor(private demoService: DemoService, public dialog: MatDialog)
  {
    super();
  }

  ngOnInit()
  {
    this.demoService.getDemoForm({page: null, sort: null, filters: null}).subscribe(data =>
    {
      console.log(data);
      this.formData = data;
      this.init();
      //this.enableButtons();
    });
  }


  private init()
  {
    const textInputName = document.getElementById("name");
    const textInputPlaceOfBirth = document.getElementById("place-of-birth");
    const textInputEmail = document.getElementById("email");
    const textInputRole = document.getElementById("role");
    const numericInputAmount = document.getElementById("amount");
    const numericInputCountSamples = document.getElementById("count-samples");
    const dateInputCheckIn = document.getElementById("check-in-date");
    const dateInputCheckOut = document.getElementById("check-out-date");
    const dateRangeInput = document.getElementById("date-range");
    const dateRangeInput2 = document.getElementById("date-range2");
    const select = document.getElementById("select");
    const select2 = document.getElementById("select2");
    const select3 = document.getElementById("select3");
    const table = document.getElementById("table-multiselect");
    const buttonOk = document.getElementById("button-ok");
    const buttonCancel = document.getElementById("button-cancel");
    this.myForm = document.getElementById("myform");

    textInputName.formdata = this.formData;
    textInputPlaceOfBirth.formdata = this.formData;
    textInputEmail.formdata = this.formData;
    textInputRole.formdata = this.formData;
    numericInputAmount.formdata = this.formData;
    numericInputCountSamples.formdata = this.formData;
    dateInputCheckIn.formdata = this.formData;
    dateInputCheckOut.formdata = this.formData;
    dateRangeInput.formdata = this.formData;
    dateRangeInput2.formdata = this.formData;
    select.formdata = this.formData;
    select2.formdata = this.formData;
    select3.formdata = this.formData;
    table.formdata = this.formData;
    buttonOk.formdata = this.formData;
    buttonCancel.formdata = this.formData;

    myForm.addEventListener('onDataChange', e => {
      console.log(e.detail.data);
      // this.demoService.submitDemoForm({id: '', widgetDataMap: submitData}).subscribe(
      //   () => console.log('sumbitted'),
      //   error => console.log(error));
    })

    buttonOk.addEventListener('click', e => {
      console.log(myForm.formgroup);
      myForm.formgroup.markAllAsTouched();
      if (!myForm.formgroup.valid)
      {
        console.warn('Data is invalid!');
      }
      else
      {
        myForm.formdata = this.formData;
      }
    })
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
    console.log(this.myForm.formgroup);
    this.myForm.formgroup.markAllAsTouched();
    if (!this.myForm.formgroup.valid)
    {
      console.warn('Data is invalid!');
    }
    else
    {
      this.myForm.formdata = this.formData;
    }

    //
    // this.formGroup.markAllAsTouched();
    // if (!this.formGroup.valid)
    // {
    //   console.warn('Data is invalid!');
    // }
    // else
    // {
    //   const submitData = this.getSubmitData();
    //   console.log(submitData);
    //
    //   this.demoService.submitDemoForm({id: '', widgetDataMap: submitData}).subscribe(
    //     () => console.log('sumbitted'),
    //     error => console.log(error));
    // }
  }


  // onTableReload($event: TableReloadEvent)
  // {
  //   this.demoService.getDemoForm({
  //     page: $event.page,
  //     sort: $event.sort,
  //     filters: $event.filters
  //   }).subscribe(data =>
  //   {
  //     console.log(data);
  //     $event.dataSource.setWidgetData(<Ngface.Table> data.widgets['table-multiselect']);
  //     if (this.formData)
  //     {
  //       this.formData.widgets['table-multiselect'] = data.widgets['table-multiselect'];
  //     }
  //   });
  // }


  // onDetails()
  // {
  //   this.doEditAction(this.getSingleSelectTableSelectedRow());
  // }

  onSingleSelectTableRowClick()
  {
    this.enableButtons();
  }

  // onTableActionClick($event: ActionClickEvent)
  // {
  //   if ($event.actionId === 'edit')
  //   {
  //     this.doEditAction($event.row);
  //   }
  // }

  // private doEditAction(row: Ngface.Row | undefined)
  // {
  //   if (!row)
  //   {
  //     return;
  //   }
  //
  //   // Reading dialog data from the baclend
  //   this.demoService.getTableDetailsForm(row.id).subscribe(dialogData =>
  //   {
  //     console.log(dialogData);
  //     // Open dialog
  //     const dialogRef = this.dialog.open(DemoDialog1Component, {
  //       width: '590px',
  //       data: dialogData,
  //       backdropClass: 'ngface-modal-dialog-backdrop'
  //     });
  //
  //     // Subscribe to afterClosed
  //     dialogRef.afterClosed().subscribe(result =>
  //     {
  //       console.log(result);
  //       if (result)
  //       {
  //         // Submitting new data to the backend
  //         this.demoService.submitTableDetailsForm({id: row.id, widgetDataMap: result}).subscribe(
  //           () =>
  //           {
  //             console.log('sumbitted');
  //             // reload table content
  //             this.demoService.getDemoFormTableRow(row.id).subscribe(data =>
  //             {
  //               console.log(data);
  //               // @ts-ignore
  //               row.cells = data.widgets['table-multiselect'].rows[0].cells;
  //             });
  //           },
  //           error => console.log(error));
  //       }
  //     });
  //   });
  // }


  // onTableValueSetSearch($event: TableValueSetSearchEvent)
  // {
  //   console.log($event);
  //
  //   // TODO: this function is only called in case of remote filterers, where actual value sets will be asked from the backend.
  //   // In this demo each filterer is local.
  //   // Ask the backend for updated set of criteria based on 'column' and 'searchText'
  //   // this.demoService.getColumnFilterer($event.column, $event.searchEvent.searchText).subscribe(filterer =>
  //   // {
  //   //   console.log(filterer);
  //   //   $event.searchEvent.valueSetProvider.setValueSet(filterer.valueSet);
  //   // });
  // }

  // onTableViewParamsChange($event: TableViewParamsChangeEvent)
  // {
  //   console.log($event);
  //
  //   let widgetData: Ngface.Table.Data = {
  //     type: 'Table.Data',
  //     paginator: $event.paginator,
  //     sorter: $event.sorter,
  //     filtererMap: $event.filtererMap
  //   };
  //   this.demoService.submitDemoForm({id: '', widgetDataMap: {['table-multiselect']: widgetData}}).subscribe(
  //     () => console.log(widgetData));
  // }
}

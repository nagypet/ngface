import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormBaseComponent} from '../ngface/form-base.component';
import {DemoService} from '../services/demo.service';
import {TypeModels} from '../dto-models';

@Component({
  selector: 'app-demo-dialog1',
  templateUrl: './demo-dialog1.component.html',
  styleUrls: ['./demo-dialog1.component.scss']
})
export class DemoDialog1Component extends FormBaseComponent implements OnInit
{
  constructor(public dialogRef: MatDialogRef<DemoDialog1Component>,
              @Inject(MAT_DIALOG_DATA) public data: TypeModels.Form,
              private demoService: DemoService)
  {
    super();
  }

  ngOnInit(): void
  {
    this.formData = this.data;
  }

  onCancelClick(): void
  {
    this.dialogRef.close();
  }

  onOkClick()
  {
    this.formGroup.markAllAsTouched();
    if (!this.formGroup.valid)
    {
      console.warn('Data is invalid!');
    } else
    {
      this.dialogRef.close(this.getSubmitData());
    }
  }
}

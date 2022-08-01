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

import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormBaseComponent} from '../../../../ngface/src/lib/form-base.component';
import {Ngface} from '../../../../ngface/src/lib/ngface-models';

@Component({
  selector: 'app-demo-dialog1',
  templateUrl: './demo-dialog1.component.html',
  styleUrls: ['./demo-dialog1.component.scss']
})
export class DemoDialog1Component extends FormBaseComponent implements OnInit
{
  constructor(public dialogRef: MatDialogRef<DemoDialog1Component>,
              @Inject(MAT_DIALOG_DATA) public data: Ngface.Form)
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

/*
 * Copyright 2020-2024 the original author or authors.
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
import {MAT_DIALOG_DATA, MatDialogRef, MatDialogModule} from '@angular/material/dialog';
import {FormBaseComponent} from '../../../../../ngface/src/lib/form/form-base.component';
import {Ngface} from '../../../../../ngface/src/lib/ngface-models';
import {NgfaceButtonComponent} from '../../../../../ngface/src/lib/widgets/ngface-button/ngface-button.component';
import {NgfaceTextInputComponent} from '../../../../../ngface/src/lib/widgets/ngface-text-input/ngface-text-input.component';
import {NgfaceNumericInputComponent} from '../../../../../ngface/src/lib/widgets/ngface-numeric-input/ngface-numeric-input.component';
import {NgfaceFormComponent} from '../../../../../ngface/src/lib/form/ngface-form/ngface-form.component';
import {NgfaceAutocompleteComponent} from '../../../../../ngface/src/lib/widgets/ngface-autocomplete/ngface-autocomplete.component';
import {ResponsiveClassDirective} from '../../../../../ngface/src/lib/directives/responsive-class-directive';
import {NgfaceDialogCloseDirective} from '../../../../../ngface/src/lib/directives/ngface-dialog-close-directive';

@Component({
  selector: 'app-demo-dialog1',
  templateUrl: './table-details-dialog.component.html',
  styleUrls: ['./table-details-dialog.component.scss'],
  standalone: true,
  imports: [
    MatDialogModule,
    NgfaceFormComponent,
    NgfaceNumericInputComponent,
    NgfaceTextInputComponent,
    NgfaceButtonComponent,
    NgfaceAutocompleteComponent,
    ResponsiveClassDirective,
    NgfaceDialogCloseDirective
  ]
})
export class TableDetailsDialogComponent extends FormBaseComponent implements OnInit
{
  constructor(public dialogRef: MatDialogRef<TableDetailsDialogComponent>,
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
      this.dialogRef.close(submitData);
    }
  }
}

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

import {Component, NgZone, OnDestroy, OnInit} from '@angular/core';
import {WidgetDemoFormService} from '../../core/services/widget-demo-form.service';
import {FormBaseComponent} from '../../../../../ngface/src/lib/form/form-base.component';
import {TableDemoFormService} from '../../core/services/table-demo-form.service';
import {NgfaceButtonComponent} from '../../../../../ngface/src/lib/widgets/ngface-button/ngface-button.component';
import {NgfaceSelectComponent} from '../../../../../ngface/src/lib/widgets/ngface-select/ngface-select.component';
import {
  NgfaceDateRangeInputComponent
} from '../../../../../ngface/src/lib/widgets/ngface-date-range-input/ngface-date-range-input.component';
import {NgfaceDateInputComponent} from '../../../../../ngface/src/lib/widgets/ngface-date-input/ngface-date-input.component';
import {NgfaceNumericInputComponent} from '../../../../../ngface/src/lib/widgets/ngface-numeric-input/ngface-numeric-input.component';
import {NgfaceTextInputComponent} from '../../../../../ngface/src/lib/widgets/ngface-text-input/ngface-text-input.component';
import {NgfaceFormComponent} from '../../../../../ngface/src/lib/form/ngface-form/ngface-form.component';
import {
  AutocompleteRequest,
  NgfaceAutocompleteComponent
} from '../../../../../ngface/src/lib/widgets/ngface-autocomplete/ngface-autocomplete.component';
import {ResponsiveClassDirective} from '../../../../../ngface/src/lib/directives/responsive-class-directive';
import {SseChannel} from '../../../../../ngface/src/lib/services/ssechannel';
import {environment} from '../../../environments/environment';
import {NgfaceSse} from '../../../../../ngface/src/lib/ngface-sse-models';

// tslint:disable-next-line:no-namespace
export namespace WidgetDemoFormComponent
{
  export type Actions = 'SELECT_ALL' | 'SELECT_NONE';
}


@Component({
  selector: 'app-widget-demo-form',
  templateUrl: './widget-demo-form.component.html',
  styleUrls: ['./widget-demo-form.component.scss'],
  standalone: true,
  imports: [
    NgfaceFormComponent,
    NgfaceTextInputComponent,
    NgfaceNumericInputComponent,
    NgfaceDateInputComponent,
    NgfaceDateRangeInputComponent,
    NgfaceSelectComponent,
    NgfaceButtonComponent,
    NgfaceAutocompleteComponent,
    ResponsiveClassDirective,
  ]
})
export class WidgetDemoFormComponent extends FormBaseComponent implements OnInit, OnDestroy
{
  private sseChannel!: SseChannel;
  public lastEventId = '';

  constructor(
    private widgetDemoFormService: WidgetDemoFormService,
    private tableDemoFormService: TableDemoFormService,
    private zone: NgZone
  )
  {
    super();
  }

  ngOnInit(): void
  {
    this.sseChannel = new SseChannel(
      `${environment.baseURL}/frontend/sse/subscribe`,
      (event: MessageEvent) => this.handleServerEvent(event),
      this.zone,
      true,
      (event: MessageEvent) => this.handleServerError(event));
    this.sseChannel.open();

    this.widgetDemoFormService.getDemoForm().subscribe(form =>
    {
      console.log(form);
      this.formData = form;
    });
  }


  ngOnDestroy(): void
  {
    this.sseChannel.close();
  }


  /**
   * Asyncronous event from the backend
   * @param event
   * @private
   */
  private handleServerEvent(event: MessageEvent): void
  {
    this.lastEventId = event.lastEventId;
    const notification: NgfaceSse.SseNotification = JSON.parse(event.data);

    switch (notification.type)
    {
      case 'UPDATE':
        const updateNotification: NgfaceSse.SseUpdateNotification<number> = JSON.parse(event.data);
        if (updateNotification.subject === 'tick')
        {
          console.log(updateNotification);
        }
        break;
    }
  }


  private handleServerError(event: MessageEvent): void
  {
    console.log(event);
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

      this.widgetDemoFormService.submitDemoForm({id: '', widgetDataMap: submitData}).subscribe(
        () => console.log('sumbitted'),
        error => console.log(error));
    }
  }


  onDeleteClick(): void
  {
    this.widgetDemoFormService.deleteForm().subscribe(next =>
    {
      console.log(next);
    });
  }


  onAutocompleteRequest($event: AutocompleteRequest): void
  {
    this.tableDemoFormService.getColumnFilterer('street', $event.searchText).subscribe(filterer =>
    {
      console.log(filterer);
      $event.valueSetProvider.valueSet = filterer.valueSet;
    });
  }
}

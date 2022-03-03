import {Component, OnChanges} from '@angular/core';
import {InputBaseComponent} from '../input-base.component';
import {TypeModels} from '../../dto-models';

@Component({
  selector: 'ngface-select',
  templateUrl: './select.component.html',
  styleUrls: ['./select.component.scss']
})
export class SelectComponent extends InputBaseComponent implements OnChanges {

  constructor() {
    super();
  }

  ngOnChanges()
  {
    super.ngOnChanges();
    this.formControl.setValue(this.getData()?.data?.selected);
  }

  getData(): TypeModels.Select
  {
    let widget = this.formData?.widgets[this.widgetId];
    if (!widget || widget?.type !== 'Select')
    {
      return {
        type: 'Select',
        data: {type: 'Select.Data', options: [], selected: ''},
        placeholder: '',
        validators: [],
        label: 'undefined label',
        enabled: false,
        id: '',
        hint: ''
      };
    }
    return <TypeModels.Select> this.formData.widgets[this.widgetId];
  }

}

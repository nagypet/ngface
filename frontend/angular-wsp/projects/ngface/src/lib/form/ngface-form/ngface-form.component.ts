import {
  AfterViewInit,
  Component,
  ContentChildren,
  ElementRef,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  QueryList
} from '@angular/core';
import {NgfaceTextInputComponent} from '../../widgets/ngface-text-input/ngface-text-input.component';
import {NgfaceNumericInputComponent} from '../../widgets/ngface-numeric-input/ngface-numeric-input.component';
import {NgfaceDateInputComponent} from '../../widgets/ngface-date-input/ngface-date-input.component';
import {NgfaceDateRangeInputComponent} from '../../widgets/ngface-date-range-input/ngface-date-range-input.component';
import {NgfaceSelectComponent} from '../../widgets/ngface-select/ngface-select.component';
import {FormGroup} from '@angular/forms';
import {Ngface} from '../../ngface-models';

export class ControlData
{
  data!: { [key: string]: Ngface.WidgetData };
}

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'ngface-form',
  templateUrl: './ngface-form.component.html',
  styleUrls: ['./ngface-form.component.css']
})
export class NgfaceFormComponent implements OnInit, OnChanges, AfterViewInit
{

  get formGroup(): FormGroup
  {
    return this.formgroup;
  }

  constructor(private el: ElementRef)
  {
  }

  @Input()
  formdata?: Ngface.Form;

  // Misused here to generate a getter in the web-component
  @Input()
  private formgroup: FormGroup<{}> = new FormGroup({});

  // tslint:disable-next-line:no-output-on-prefix
  @Output()
  onDataChange: EventEmitter<ControlData> = new EventEmitter();

  @ContentChildren(NgfaceTextInputComponent, {descendants: true}) textInputComponents!: QueryList<NgfaceTextInputComponent>;
  @ContentChildren(NgfaceNumericInputComponent, {descendants: true}) numericInputComponents!: QueryList<NgfaceNumericInputComponent>;
  @ContentChildren(NgfaceDateInputComponent, {descendants: true}) dateInputComponents!: QueryList<NgfaceDateInputComponent>;
  @ContentChildren(NgfaceDateRangeInputComponent, {descendants: true}) dateRangeInputComponents!: QueryList<NgfaceDateRangeInputComponent>;
  @ContentChildren(NgfaceSelectComponent, {descendants: true}) selectInputComponents!: QueryList<NgfaceSelectComponent>;


  private static getLocalDateTime(date: Date): Date | null
  {
    if (!date)
    {
      return null;
    }

    if (date instanceof Date)
    {
      const offset = date.getTimezoneOffset();
      const convertedDate: Date = new Date();
      convertedDate.setTime(date.getTime() - (offset * 60 * 1000));
      return convertedDate;
    }
    return new Date(date);
  }

  ngOnInit(): void
  {
  }

  ngOnChanges(): void
  {
    this.onDataChange.emit({data: this.getSubmitData()});
  }

  ngAfterViewInit(): void
  {
    // This solution works only in Angular. When using the controls as web-components not.
    this.textInputComponents.forEach(comp => this.formGroup.addControl(comp.widgetid, comp.formGroupItem));
    this.numericInputComponents.forEach(comp => this.formGroup.addControl(comp.widgetid, comp.formGroupItem));
    this.dateInputComponents.forEach(comp => this.formGroup.addControl(comp.widgetid, comp.formGroupItem));
    this.dateRangeInputComponents.forEach(comp => this.formGroup.addControl(comp.widgetid, comp.formGroupItem));
    this.selectInputComponents.forEach(comp => this.formGroup.addControl(comp.widgetid, comp.formGroupItem));

    // This solution is for the web-component solution
    const allControls = this.getAllNgfaceControls(this.el.nativeElement);
    // @ts-ignore
    allControls.forEach(comp => this.formGroup.addControl(comp.widgetid, comp.get_form_group_item));

    console.log('formGroup controls:');
    console.log(this.formGroup.controls);
  }


  private getAllNgfaceControls(element: Element): Element[]
  {
    // console.log(element.tagName);
    if (!element)
    {
      return [];
    }

    const retval: Element[] = [];

    // @ts-ignore
    const collection: HTMLCollection = element.children;
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < collection.length; i++)
    {
      const child = collection[i];
      if (this.isNgfaceControl(child))
      {
        retval.push(child);
      }
      const allControlsOfChild = this.getAllNgfaceControls(child);
      allControlsOfChild.forEach((el: Element) => retval.push(el));
    }

    return retval;
  }


  private isNgfaceControl(element: Element): boolean
  {
    return element.localName === 'ngface-date-input-element'
      || element.localName === 'ngface-date-range-input-element'
      || element.localName === 'ngface-text-input-element'
      || element.localName === 'ngface-numeric-input-element'
      || element.localName === 'ngface-select-element';
  }


  getSubmitData(): { [key: string]: Ngface.WidgetData }
  {
    const submitData: { [key: string]: Ngface.WidgetData } = {};
    Object.keys(this.formGroup.controls).forEach(controlName =>
    {
      const widget = this.formdata?.widgets[controlName];
      const widgetType: string | undefined = widget?.type;
      switch (widgetType)
      {
        case 'TextInput':
        case 'NumericInput':
          submitData[controlName] = {
            type: widgetType + '.Data',
            value: this.formGroup.controls[controlName]?.value
          } as Ngface.Value<any>;
          break;

        case 'DateInput':
        case 'DateTimeInput':
          // Converting to local date without time zone information
          const myDate = this.formGroup.controls[controlName]?.value;
          submitData[controlName] = {
            type: widgetType + '.Data',
            value: NgfaceFormComponent.getLocalDateTime(myDate)
          } as Ngface.Value<any>;
          break;

        case 'DateRangeInput':
          submitData[controlName] = {
            type: widgetType + '.Data',
            startDate: NgfaceFormComponent.getLocalDateTime(this.formGroup.controls[controlName]?.value?.start),
            endDate: NgfaceFormComponent.getLocalDateTime(this.formGroup.controls[controlName]?.value?.end)
          } as Ngface.DateRangeInput.Data;
          break;

        case 'Select':
          const selected = this.formGroup.controls[controlName]?.value;
          const selectedOption: { [index: string]: string } = {};
          selectedOption[selected] = widget?.data.options[selected];
          submitData[controlName] = {type: widgetType + '.Data', options: selectedOption, selected} as Ngface.Select.Data;
          break;
      }
    });

    return submitData;
  }
}

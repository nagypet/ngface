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
import {NgfaceTextInputComponent} from '../ngface-text-input/ngface-text-input.component';
import {NgfaceNumericInputComponent} from '../ngface-numeric-input/ngface-numeric-input.component';
import {NgfaceDateInputComponent} from '../ngface-date-input/ngface-date-input.component';
import {NgfaceDateRangeInputComponent} from '../ngface-date-range-input/ngface-date-range-input.component';
import {NgfaceSelectComponent} from '../ngface-select/ngface-select.component';
import {FormGroup} from '@angular/forms';
import {Ngface} from '../ngface-models';

export class ControlData
{
  data!: { [key: string]: Ngface.WidgetData };
}

@Component({
  selector: 'ngface-form',
  templateUrl: './ngface-form.component.html',
  styleUrls: ['./ngface-form.component.css']
})
export class NgfaceFormComponent implements OnInit, OnChanges, AfterViewInit
{
  @Input()
  formdata?: Ngface.Form;

  @Input()
  formgroup = new FormGroup({});

  @Output()
  onDataChange: EventEmitter<ControlData> = new EventEmitter();

  @ContentChildren(NgfaceTextInputComponent, {descendants: true}) textInputComponents!: QueryList<NgfaceTextInputComponent>;
  @ContentChildren(NgfaceNumericInputComponent, {descendants: true}) numericInputComponents!: QueryList<NgfaceNumericInputComponent>;
  @ContentChildren(NgfaceDateInputComponent, {descendants: true}) dateInputComponents!: QueryList<NgfaceDateInputComponent>;
  @ContentChildren(NgfaceDateRangeInputComponent, {descendants: true}) dateRangeInputComponents!: QueryList<NgfaceDateRangeInputComponent>;
  @ContentChildren(NgfaceSelectComponent, {descendants: true}) selectInputComponents!: QueryList<NgfaceSelectComponent>;

  get formGroup(): FormGroup
  {
    return this.formgroup;
  }

  constructor(private el: ElementRef)
  {
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
    this.textInputComponents.forEach(comp => comp.formgroup = this.formGroup);
    this.numericInputComponents.forEach(comp => comp.formgroup = this.formGroup);
    this.dateInputComponents.forEach(comp => comp.formgroup = this.formGroup);
    this.dateRangeInputComponents.forEach(comp => comp.formgroup = this.formGroup);
    this.selectInputComponents.forEach(comp => comp.formgroup = this.formGroup);

    // This solution is for the web-component solution
    var allControls = this.getAllNgfaceControls(this.el.nativeElement);
    // @ts-ignore
    allControls.forEach(comp => comp.formgroup = this.formGroup);
  }

  private getAllNgfaceControls(element: Element): Element[]
  {
    //console.log(element.tagName);
    if (!element)
    {
      return [];
    }

    let retval: Element[] = [];

    // @ts-ignore
    const collection: HTMLCollection = element.children;
    for (let i = 0; i < collection.length; i++)
    {
      let child = collection[i];
      if (this.isNgfaceControl(child))
      {
        retval.push(child);
      }
      var allControlsOfChild = this.getAllNgfaceControls(child);
      allControlsOfChild.forEach(i => retval.push(i));
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
    let submitData: { [key: string]: Ngface.WidgetData } = {};
    Object.keys(this.formGroup.controls).forEach(controlName =>
    {
      let widget = this.formdata?.widgets[controlName];
      let widgetType: string | undefined = widget?.type;
      switch (widgetType)
      {
        case 'TextInput':
        case 'NumericInput':
          submitData[controlName] = <Ngface.Value<any>> {
            type: widgetType + '.Data',
            value: this.formGroup.controls[controlName]?.value
          };
          break;

        case 'DateInput':
        case 'DateTimeInput':
          // Converting to local date without time zone information
          let myDate = this.formGroup.controls[controlName]?.value;
          submitData[controlName] = <Ngface.Value<any>> {
            type: widgetType + '.Data',
            value: NgfaceFormComponent.getLocalDateTime(myDate)
          };
          break;

        case 'DateRangeInput':
          submitData[controlName] = <Ngface.DateRangeInput.Data> {
            type: widgetType + '.Data',
            startDate: NgfaceFormComponent.getLocalDateTime(this.formGroup.controls[controlName]?.value?.start),
            endDate: NgfaceFormComponent.getLocalDateTime(this.formGroup.controls[controlName]?.value?.end)
          };
          break;

        case 'Select':
          let selected = this.formGroup.controls[controlName]?.value;
          let selectedOption: { [index: string]: string } = {};
          selectedOption[selected] = widget?.data.options[selected];
          submitData[controlName] = <Ngface.Select.Data> {type: widgetType + '.Data', options: selectedOption, selected: selected};
          break;
      }
    });

    return submitData;
  }


  private static getLocalDateTime(date: Date): Date | null
  {
    if (!date)
    {
      return null;
    }

    if (date instanceof Date)
    {
      const offset = date.getTimezoneOffset();
      let convertedDate: Date = new Date();
      convertedDate.setTime(date.getTime() - (offset * 60 * 1000));
      return convertedDate;
    }
    return new Date(date);
  }
}

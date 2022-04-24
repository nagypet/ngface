import {Component, OnInit} from '@angular/core';

export interface SearchItem
{
  text: string;
  selected: boolean;
}

@Component({
  selector: 'ngface-excel-filter',
  templateUrl: './excel-filter.component.html',
  styleUrls: ['./excel-filter.component.scss']
})
export class ExcelFilterComponent implements OnInit
{

  choises: SearchItem[] = [];

  constructor()
  {
  }

  ngOnInit(): void
  {
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
    this.choises.push({text: 'valami', selected: false});
  }

  onItemSelected(choice: SearchItem)
  {
    choice.selected = !choice.selected;
  }

  onCancel()
  {

  }

  onOk()
  {

  }
}

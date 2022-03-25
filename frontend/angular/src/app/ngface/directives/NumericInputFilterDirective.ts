import {Directive, ElementRef, HostListener} from '@angular/core';

@Directive({
  selector: '[numericInputFilter]'
})
export class NumericInputFilterDirective
{

  constructor(private el: ElementRef)
  {
  }

  @HostListener('keydown', ['$event']) onKeyDown(event: any)
  {
    const e = <KeyboardEvent> event;

    if (['Backspace', 'Tab', 'Enter', 'Delete', 'Escape', '.', ',', '-'].indexOf(e.key) !== -1 ||
      // Allow: Ctrl+A
      (e.key === 'a' && e.ctrlKey === true) ||
      // Allow: Ctrl+C
      (e.key === 'c' && e.ctrlKey === true) ||
      // Allow: Ctrl+X
      (e.key === 'x' && e.ctrlKey === true) ||
      // Allow: Ctrl+V
      (e.key === 'v' && e.ctrlKey === true) ||
      // Allow: home, end, left, right
      (e.keyCode >= 35 && e.keyCode <= 39))
    {
      // let it happen, don't do anything
      return;
    }
    // Ensure that it is a number and stop the keypress
    if (e.shiftKey || e.key < '0' || e.key > '9')
    {
      e.preventDefault();
    }
  }
}

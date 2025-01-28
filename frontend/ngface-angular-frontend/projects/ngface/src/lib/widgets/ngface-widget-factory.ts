import {Ngface} from '../ngface-models';

export class NgfaceWidgetFactory
{

  public static createTextInput(id = '', label = 'undefined label', value = '', validators: Ngface.Validator[] = [])
  {
    return {
      id: id,
      type: 'TextInput',
      label: label,
      placeholder: label,
      hint: '',
      data: {type: 'TextInput.Data', value: value},
      enabled: true,
      validators: validators
    } as Ngface.TextInput;
  }

  public static createButton(id = '', label = 'undefined label', style: Ngface.Style = 'PRIMARY')
  {
    return {
      id: id,
      type: 'Button',
      label: label,
      style: style,
      enabled: true
    } as Ngface.Button;
  }
}

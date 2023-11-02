import {Ngface} from '../../ngface-models';

export class AutocompleteValueSetProvider
{
    // tslint:disable-next-line:variable-name
    private _valueSet?: Ngface.ValueSet;

    set valueSet(value: Ngface.ValueSet)
    {
        this._valueSet = value;
    }
}

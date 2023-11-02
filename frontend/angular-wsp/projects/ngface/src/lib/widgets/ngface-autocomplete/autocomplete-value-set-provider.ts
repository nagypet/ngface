import {Ngface} from '../../ngface-models';
import {map, Observable, startWith, Subject} from 'rxjs';

export class AutocompleteValueSetProvider
{
    // valueSet
    // tslint:disable-next-line:variable-name
    private _valueSet?: Ngface.ValueSet;

    set valueSet(value: Ngface.ValueSet)
    {
        this._valueSet = value;
        if (!value.remote)
        {
            this._filteredOptions = this._searchText.pipe(
                startWith(''),
                map(i => this.filter(i || ''))
            );
        }
        else
        {
            const items = value.values.map(i => i.text);
            if (value.truncated)
            {
                items.push('...');
            }
            this._filteredOptions = new Observable<string[]>(observer =>
            {
                observer.next(items);
                observer.complete();
            });
        }
    }

    // searchText
    // tslint:disable-next-line:variable-name
    private _searchText: Subject<string> = new Subject<string>();

    set searchText(value: string)
    {
        this._searchText.next(value);
    }

    // filteredOptions
    // tslint:disable-next-line:variable-name
    private _filteredOptions: Observable<string[]> = new Observable<string[]>();
    get filteredOptions(): Observable<string[]>
    {
        return this._filteredOptions;
    }

    private filter(value: string): string[]
    {
        const filterValue = value.toLowerCase();

        if (!this._valueSet)
        {
            return [];
        }

        return this._valueSet.values
            .map(i => i.text)
            .filter(item => item.toLowerCase().includes(filterValue));
    }

    public isRemote(): boolean
    {
        return !!this._valueSet?.remote;
    }
}

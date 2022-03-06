/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 2.17.558 on 2022-03-06 21:18:27.

export namespace TypeModels {

    export interface Button extends Widget<VoidWidgetData, Button> {
        data: VoidWidgetData;
        style: Style;
    }

    export interface Form {
        id: string;
        widgets: { [index: string]: Widget<any, any> };
    }

    export interface DateInput extends Input<DateInput.Data, Date, DateInput> {
        data: DateInput.Data;
    }

    export namespace DateInput {

        export interface Data extends Value<Date> {
            value: Date;
        }

    }

    export interface DateRangeInput extends Input<DateRangeInput.Data, void, DateRangeInput> {
        data: DateRangeInput.Data;
        placeholder2: string;
        validators2: Validator<any>[];
    }

    export namespace DateRangeInput {

        export interface Data extends WidgetData {
            startDate: Date;
            endDate: Date;
        }

    }

    export interface DateTimeInput extends Input<DateTimeInput.Data, Date, DateTimeInput> {
        data: DateTimeInput.Data;
    }

    export namespace DateTimeInput {

        export interface Data extends Value<Date> {
            value: Date;
        }

    }

    export interface NumericInput extends Input<NumericInput.Data, number, NumericInput> {
        data: NumericInput.Data;
        precision: number;
        prefix: string;
        suffix: string;
    }

    export namespace NumericInput {

        export interface Data extends Value<number> {
            value: number;
        }

    }

    export interface Select extends Input<Select.Data, void, Select> {
        data: Select.Data;
    }

    export namespace Select {

        export interface Data extends WidgetData {
            options: { [index: string]: string };
            selected: string;
        }

    }

    export interface Option {
        id: string;
        value: string;
    }

    export interface TextInput extends Input<TextInput.Data, string, TextInput> {
        data: TextInput.Data;
    }

    export namespace TextInput {

        export interface Data extends Value<string> {
            value: string;
        }

    }

    export interface Email extends Validator<Email> {
    }

    export interface Max extends Validator<Max> {
        max: number;
    }

    export interface Min extends Validator<Min> {
        min: number;
    }

    export interface Pattern extends Validator<Pattern> {
        pattern: string;
    }

    export interface Required extends Validator<Required> {
    }

    export interface Size extends Validator<Size> {
        min: number;
        max: number;
    }

    export interface Column {
        id: string;
        text: string;
        sortable: boolean;
    }

    export interface Paginator {
        pageSize: number;
        length: number;
    }

    export interface Row {
        id: string;
        cells: { [index: string]: string };
    }

    export interface Table extends Widget<Table.Data, Table> {
        data: Table.Data;
    }

    export namespace Table {

        export interface Data extends WidgetData {
            columns: Column[];
            rows: Row[];
            paginator: Paginator;
        }

    }

    export interface VoidWidgetData extends WidgetData {
    }

    export interface Widget<WD, SUB> {
        type: string;
        id: string;
        label: string;
        hint: string;
        enabled: boolean;
        data: WD;
    }

    export interface Validator<SUB> {
        type: string;
        message: string;
    }

    export interface WidgetData {
        type: string;
    }

    export interface Input<WD, V, SUB> extends Widget<WD, SUB> {
        placeholder: string;
        validators: Validator<any>[];
    }

    export interface Value<V> extends WidgetData {
        value: V;
    }

    export type Style = "NONE" | "PRIMARY" | "ACCENT" | "WARN";

}

/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 2.36.1070 on 2022-07-08 10:31:09.

export namespace Ngface {

    export interface DataRetrievalParams {
        page: DataRetrievalParams.Page | undefined;
        sort: DataRetrievalParams.Sort | undefined;
        filters: DataRetrievalParams.Filter[] | undefined;
    }

    export interface SubmitFormData {
        id: string;
        widgetDataMap: { [index: string]: WidgetData };
    }

    export interface TableActionParams {
        actionId: string;
        rowId: string;
    }

    export interface Button extends Widget<VoidWidgetData, Button> {
        data: VoidWidgetData;
        style: Style;
    }

    export interface Form {
        id: string;
        title: string;
        widgets: { [index: string]: Widget<any, any> };
    }

    export interface DateInput extends Input<DateInput.Data, Date, DateInput> {
        data: DateInput.Data;
    }

    export namespace DateInput {

        export interface Data extends Value<Date> {
            type: "DateInput.Data";
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
            type: "DateRangeInput.Data";
            startDate: Date;
            endDate: Date;
        }

    }

    export interface DateTimeInput extends Input<DateTimeInput.Data, Date, DateTimeInput> {
        data: DateTimeInput.Data;
    }

    export namespace DateTimeInput {

        export interface Data extends Value<Date> {
            type: "DateTimeInput.Data";
            value: Date;
        }

    }

    export interface NumericInput extends Input<NumericInput.Data, number, NumericInput> {
        data: NumericInput.Data;
        format: NumericFormat;
    }

    export namespace NumericInput {

        export interface Data extends Value<number> {
            type: "NumericInput.Data";
            value: number;
        }

    }

    export interface Select extends Input<Select.Data, void, Select> {
        data: Select.Data;
    }

    export namespace Select {

        export interface Data extends WidgetData {
            type: "Select.Data";
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
            type: "TextInput.Data";
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

    export interface Action {
        id: string;
        label: string;
        icon: string;
        enabled: boolean;
    }

    export interface Column {
        id: string;
        text: string;
        sortable: boolean;
        size: Column.Size;
        textAlign: TextAlign;
    }

    export interface Filterer {
        column: string;
        valueSet: ValueSet;
        searchText: string;
        active: boolean;
    }

    export interface Paginator {
        pageIndex: number;
        pageSize: number;
        length: number;
        pageSizeOptions: number[];
    }

    export interface Row {
        id: string;
        cells: { [index: string]: Cell<any, any> };
        selected: boolean;
    }

    export interface Sorter {
        column: string;
        direction: Direction;
    }

    export interface Table extends Widget<Table.Data, Table> {
        data: Table.Data;
        columns: { [index: string]: Column };
        rows: Row[];
        selectMode: SelectMode;
        notification: string;
    }

    export namespace Table {

        export interface Data extends WidgetData {
            type: "Table.Data";
            paginator: Paginator | undefined;
            sorter: Sorter | undefined;
            filtererMap: { [index: string]: Filterer };
        }

    }

    export interface ValueSet {
        remote: boolean;
        truncated: boolean;
        values: ValueSet.Item[];
    }

    export namespace ValueSet {

        export interface Item {
            text: string;
            selected: boolean;
        }

    }

    export interface ActionCell extends Cell<Action[], ActionCell> {
        value: Action[];
    }

    export interface Cell<V, SUB> {
        type: string;
        value: V;
        label: string;
    }

    export interface NumericCell extends Cell<number, NumericCell> {
        value: number;
        format: NumericFormat;
    }

    export interface TextCell extends Cell<string, TextCell> {
        value: string;
    }

    export namespace DataRetrievalParams {

        export interface Page {
            index: number;
            size: number;
        }

    }

    export namespace DataRetrievalParams {

        export interface Sort {
            column: string;
            direction: Direction;
        }

    }

    export namespace DataRetrievalParams {

        export interface Filter {
            column: string;
            valueSet: DataRetrievalParams.Filter.Item[];
        }

    }

    export interface WidgetData {
        type: "WidgetData" | "DateRangeInput.Data" | "Select.Data" | "Table.Data" | "VoidWidgetData" | "Value" | "DateInput.Data" | "DateTimeInput.Data" | "NumericInput.Data" | "TextInput.Data";
    }

    export interface VoidWidgetData extends WidgetData {
        type: "VoidWidgetData";
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

    export interface NumericFormat extends AbstractFormat {
        precision: number;
        prefix: string;
        suffix: string;
    }

    export namespace DataRetrievalParams.Filter {

        export interface Item {
            text: string | undefined;
        }

    }

    export interface Input<WD, V, SUB> extends Widget<WD, SUB> {
        placeholder: string;
        validators: Validator<any>[];
    }

    export interface Value<V> extends WidgetData {
        type: "Value" | "DateInput.Data" | "DateTimeInput.Data" | "NumericInput.Data" | "TextInput.Data";
        value: V;
    }

    export interface AbstractFormat {
        validators: Validator<any>[];
    }

    export type Style = "NONE" | "PRIMARY" | "ACCENT" | "WARN";

    export namespace Column {

        export type Size = "AUTO" | "XS" | "S" | "M" | "L" | "XL" | "TIMESTAMP" | "NUMBER";

    }

    export type TextAlign = "LEFT" | "CENTER" | "RIGHT";

    export type SelectMode = "NONE" | "SINGLE" | "MULTI" | "CHECKBOX";

    export type Direction = "ASC" | "DESC" | "UNDEFINED";

}

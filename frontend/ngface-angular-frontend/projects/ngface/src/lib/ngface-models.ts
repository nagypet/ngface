/*
 * Copyright 2020-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2024-08-10 08:17:44.

export namespace Ngface {

    export interface DataRetrievalParams {
        page: DataRetrievalParams.Page | null;
        sort: DataRetrievalParams.Sort | null;
        filters: DataRetrievalParams.Filter[] | null;
    }

    export interface Menu {
        items: Menu.Item[];
        defaultItemId: string;
    }

    export interface RowSelectParams<T> {
        selectMode: RowSelectParams.SelectMode;
        rows: RowSelectParams.Row<T>[];
    }

    export interface SubmitFormData {
        id: string;
        widgetDataMap: { [index: string]: WidgetData };
    }

    export interface TableActionParams<T> {
        actionTriggerMode: ActionTriggerMode;
        actionId: string;
        rowId: T | null;
    }

    export interface Button extends Widget<VoidWidgetData, Button> {
        data: VoidWidgetData;
        style: Style;
        badge: string;
    }

    export interface Form {
        id: string;
        title: string;
        widgets: { [index: string]: Widget<any, any> };
    }

    export interface FormattedText extends Widget<FormattedText.Data, FormattedText> {
        data: FormattedText.Data;
    }

    export namespace FormattedText {

        export interface Data extends Value<string> {
            type: "FormattedText.Data";
            value: string;
        }

    }

    export interface Autocomplete extends Input<Data, string, Autocomplete> {
        data: Data;
    }

    export interface Data extends Value<string> {
        type: "Autocomplete.Data";
        value: string;
        extendedReadOnlyData: ExtendedReadOnlyData;
    }

    export interface ExtendedReadOnlyData {
        valueSet: ValueSet;
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
        validators2: Validator[];
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

    export interface Email extends Validator {
    }

    export interface Max extends Validator {
        max: number;
    }

    export interface Min extends Validator {
        min: number;
    }

    export interface Pattern extends Validator {
        pattern: string;
    }

    export interface Required extends Validator {
    }

    export interface Size extends Validator {
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

    export interface FiltererFactory {
        filtererDefMap: { [index: string]: FiltererDef };
    }

    export interface FiltererDef {
        column: string;
        remote: boolean;
        valueProvider: Function<string, string[]>;
    }

    export interface Paginator {
        pageIndex: number;
        pageSize: number;
        length: number;
        pageSizeOptions: number[];
    }

    export interface Row<T> {
        id: T;
        cells: { [index: string]: Cell<any, any> };
        selected: boolean;
    }

    export interface Sorter {
        column: string;
        direction: Direction;
    }

    export interface Table<T> extends Widget<Table.Data, Table<T>> {
        data: Table.Data;
        columns: { [index: string]: Column };
        rows: Row<T>[];
        totalRow: Row<T> | null;
        selectMode: Table.SelectMode;
        notification: string;
    }

    export namespace Table {

        export interface Data extends WidgetData {
            type: "Table.Data";
            paginator: Paginator | null;
            sorter: Sorter | null;
            filtererMap: { [index: string]: Filterer };
        }

    }

    export interface TableDataBuilder {
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

    export interface Titlebar extends Widget<VoidWidgetData, Titlebar> {
        data: VoidWidgetData;
        appTitle: string;
        version: string;
        menu: Menu;
        actions: Action[];
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

    export namespace Menu {

        export interface Item {
            id: string;
            label: string;
            icon: string;
            submenu: Menu;
        }

    }

    export namespace RowSelectParams {

        export interface Row<T> {
            id: T;
            selected: boolean;
        }

    }

    export interface WidgetData {
        type: "WidgetData" | "DateRangeInput.Data" | "Select.Data" | "Table.Data" | "VoidWidgetData" | "Value" | "FormattedText.Data" | "Autocomplete.Data" | "DateInput.Data" | "DateTimeInput.Data" | "NumericInput.Data" | "TextInput.Data";
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

    export interface Validator {
        type: string;
        message: string;
    }

    export interface NumericFormat extends AbstractFormat {
        precision: number;
        prefix: string;
        suffix: string;
        digitGrouping: boolean;
    }

    export interface Function<T, R> {
    }

    export namespace DataRetrievalParams.Filter {

        export interface Item {
            text: string | null;
        }

    }

    export interface Value<V> extends WidgetData {
        type: "Value" | "FormattedText.Data" | "Autocomplete.Data" | "DateInput.Data" | "DateTimeInput.Data" | "NumericInput.Data" | "TextInput.Data";
        value: V;
    }

    export interface Input<WD, V, SUB> extends Widget<WD, SUB> {
        placeholder: string;
        validators: Validator[];
    }

    export interface AbstractFormat {
        validators: Validator[];
    }

    export type Direction = "ASC" | "DESC" | "UNDEFINED";

    export type Style = "NONE" | "PRIMARY" | "ACCENT" | "WARN";

    export namespace Column {

        export type Size = "AUTO" | "XS" | "S" | "M" | "L" | "XL" | "TIMESTAMP" | "NUMBER";

    }

    export type TextAlign = "LEFT" | "CENTER" | "RIGHT";

    export namespace Table {

        export type SelectMode = "NONE" | "SINGLE" | "MULTI" | "CHECKBOX";

    }

    export namespace RowSelectParams {

        export type SelectMode = "ALL_CHECKED" | "ALL_UNCHECKED" | "SINGLE";

    }

    export type ActionTriggerMode = "ALL_SELECTED" | "SINGLE";

}

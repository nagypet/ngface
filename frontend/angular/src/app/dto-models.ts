/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 2.17.558 on 2022-02-27 10:50:41.

export namespace TypeModels {

    export interface Button extends Widget<Button> {
        style: Style;
    }

    export interface Form {
        id: string;
        widgets: { [index: string]: Widget<any> };
    }

    export interface DateInput extends Input<Date, DateInput> {
        value: Date;
    }

    export interface DateRangeInput extends Input<Date, DateRangeInput> {
        value: Date;
        endDate: Date;
    }

    export interface DateTimeInput extends Input<Date, DateTimeInput> {
        value: Date;
    }

    export interface NumericInput extends Input<number, NumericInput> {
        value: number;
        precision: number;
        prefix: string;
        suffix: string;
    }

    export interface TextInput extends Input<string, TextInput> {
        value: string;
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

    export interface Widget<SUB> {
        type: string;
        id: string;
        label: string;
        hint: string;
        enabled: boolean;
    }

    export interface Input<T, SUB> extends Widget<SUB> {
        placeholder: string;
        value: T;
        validators: Validator<any>[];
    }

    export interface Validator<SUB> {
        type: string;
        message: string;
    }

    export type Style = "NONE" | "PRIMARY" | "ACCENT" | "WARN";

}

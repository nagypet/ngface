/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 2.17.558 on 2022-02-20 15:40:32.

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

    export interface DateTimeInput extends Input<Date, DateTimeInput> {
        value: Date;
    }

    export interface NumericInput extends Input<number, NumericInput> {
        value: number;
    }

    export interface TextInput extends Input<string, TextInput> {
        value: string;
    }

    export interface Min extends Constraint<Min> {
        min: number;
    }

    export interface Required extends Constraint<Required> {
    }

    export interface Widget<SUB> {
        type: string;
        id: string;
        label: string;
        tooltip: string;
        enabled: boolean;
    }

    export interface Input<T, SUB> extends Widget<SUB> {
        placeholder: string;
        value: T;
        constraints: Constraint<any>[];
    }

    export interface Constraint<SUB> {
        type: string;
        message: string;
    }

    export type Style = "NONE" | "PRIMARY" | "ACCENT" | "WARN";

}

/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 2.36.1070 on 2023-12-17 09:06:40.

export namespace NgfaceSse {

    export interface SseMessageNotification extends SseNotification {
        level: SseMessageNotification.Level;
        message: string | null;
        details: string | null;
        errorText: string | null;
    }

    export interface SseNotification {
        type: SseNotification.Type;
    }

    export interface SseReloadNotification extends SseNotification {
    }

    export interface SseUpdateNotification extends SseNotification {
        subject: string;
        jobIds: number[];
    }

    export namespace SseMessageNotification {

        export type Level = "INFO" | "WARNING" | "ERROR";

    }

    export namespace SseNotification {

        export type Type = "RELOAD" | "MESSAGE" | "UPDATE";

    }

}

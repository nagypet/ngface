/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2024-10-03 14:51:41.

export namespace NgfaceSse {

    export interface SseMessageNotification extends SseNotification {
        level: SseMessageNotification.Level;
        message?: string;
        details?: string;
        errorText?: string;
    }

    export interface SseNotification {
        type: SseNotification.Type;
        subject: string;
    }

    export interface SseReloadNotification extends SseNotification {
    }

    export interface SseUpdateNotification<T> extends SseNotification {
        jobIds: T[];
    }

    export namespace SseMessageNotification {

        export type Level = "INFO" | "WARNING" | "ERROR";

    }

    export namespace SseNotification {

        export type Type = "RELOAD" | "MESSAGE" | "UPDATE";

    }

}

/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2025-10-19 12:33:30.

export namespace NgfaceSse {

    export interface SseMessageNotification extends SseNotification {
        type: "MESSAGE";
        level: SseMessageNotification.Level;
        message?: string;
        details?: string;
        errorText?: string;
    }

    export interface SseNotification extends Serializable {
        type: "MESSAGE" | "RELOAD" | "UPDATE";
        subject: string;
        sender: string;
    }

    export interface SseReloadNotification extends SseNotification {
        type: "RELOAD";
    }

    export interface SseUpdateNotification<T> extends SseNotification {
        type: "UPDATE";
        jobIds: T[];
    }

    export interface Serializable {
    }

    export namespace SseMessageNotification {

        export type Level = "INFO" | "WARNING" | "ERROR";

    }

    export namespace SseNotification {

        export type Type = "RELOAD" | "MESSAGE" | "UPDATE";

    }

}

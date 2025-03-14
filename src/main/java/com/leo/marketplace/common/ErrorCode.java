package com.leo.marketplace.common;

/**
 * Error Code
 *
 */
public enum ErrorCode {

    SUCCESS(0, "ok", ""),
    PARAMS_ERROR(40000, "Request parameter error", ""),
    NULL_ERROR(40001, "Requested data is empty", ""),
    NOT_LOGIN(40100, "Not logged in", ""),
    NO_AUTH(40101, "Not authorised", ""),
    SYSTEM_ERROR(50000, "Internal exception", "");

    private final int code;
    private final String message;
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}

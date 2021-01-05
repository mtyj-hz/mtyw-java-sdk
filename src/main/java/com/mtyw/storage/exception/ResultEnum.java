package com.mtyw.storage.exception;

public enum ResultEnum {

    SUCCESS(0, "成功"),
    DEFAULT_ERROR(9999, "未指定的异常"),
    ;

    private final int code;

    private final String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

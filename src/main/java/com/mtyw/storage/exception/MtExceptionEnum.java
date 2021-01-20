package com.mtyw.storage.exception;

public enum MtExceptionEnum implements ServiceExceptionEnum {
    ILLEGAL_PARAM(10001, "所有参数必传"),
    FILE_SIZE_ERROR(10002, "文件大小不一致"),

    UNKNOWN_ERROR(99999, "未知错误"),
    ;

    private final Integer code;
    private final String message;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    MtExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}

package com.mtyw.storage.exception;

public enum MtExceptionEnum implements ServiceExceptionEnum {
    ILLEGAL_PARAM(10001, "所有参数必传"),
    FILE_SIZE_ERROR(10002, "文件大小不一致"),
    FILE_URI_ERROR(10003, "下载文件uri地址异常"),

    UNKNOWN_ERROR(99999, "未知错误"),

    IPFS_DIRECTORY_LIST_TIMEOUT(10001, "getIpfsDirectoryList timeout!"),
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

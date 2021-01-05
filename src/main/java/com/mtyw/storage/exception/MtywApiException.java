package com.mtyw.storage.exception;


public class MtywApiException extends RuntimeException  {
    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MtywApiException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public MtywApiException(String message) {
        this.code = ResultEnum.DEFAULT_ERROR.getCode();
        this.message = message;
    }

    public MtywApiException(String message, Throwable ex) {
        super(message, ex);
    }

    public MtywApiException(ServiceExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

}

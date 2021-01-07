package com.mtyw.storage.model.response;

import com.mtyw.storage.exception.MtywApiException;
import com.mtyw.storage.exception.MtExceptionEnum;
import com.mtyw.storage.exception.ResultEnum;

public class ResultResponse<T> {

    private boolean success;

    private int code;

    private String msg;

    private T data;

    private long total;
    public ResultResponse() {};

    public static <T> ResultResponse<T> suc() {
        ResultResponse<T> result = new ResultResponse<>();
        result.setSuccess(true);
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMessage());
        return result;
    }

    public static <T> ResultResponse<T> suc(T data) {
        return new ResultResponse<T>(true, 0, "success", data);
    }

    public static <T> ResultResponse<T> suc(T data, long total) {
        return new ResultResponse<T>(true, 0, "success", data, total);
    }

    public static <T> ResultResponse<T> fail(String msg) {
        return new ResultResponse<T>(false, 9999, msg, null);
    }

    public static <T> ResultResponse<T> fail(MtExceptionEnum mtExceptionEnum) {
        return new ResultResponse<T>(false, mtExceptionEnum.getCode(), mtExceptionEnum.getMessage(), null);
    }

    public static <T> ResultResponse<T> fail(Integer code, String msg) {
        return new ResultResponse<T>(false, code, msg, null);
    }

    public static <T> ResultResponse<T> fail(MtywApiException e) {
        return new ResultResponse<T>(false, e.getCode(), e.getMessage(), null);
    }

    private ResultResponse(T data) {
        this.code = 0;
        this.data = data;
    }

    private ResultResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResultResponse(String codeMsg) {
        this.code = 9999;
        if (codeMsg != null) {
            this.msg = codeMsg;
        }
    }

    public ResultResponse(boolean success, int code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.total = 0;
    }

    public ResultResponse(boolean success, int code, String msg, T data, long total) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.total = total;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ResultResponse{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", total=" + total +
                '}';
    }
}

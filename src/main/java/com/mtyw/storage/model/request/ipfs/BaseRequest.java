package com.mtyw.storage.model.request.ipfs;

/**
 * @author lt
 * @Date 13:48 2021/1/6
 */
public class BaseRequest {

    private String sign;
    private Long timestamp;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public BaseRequest(String sign, Long timestamp) {
        this.sign = sign;
        this.timestamp = timestamp;
    }
}

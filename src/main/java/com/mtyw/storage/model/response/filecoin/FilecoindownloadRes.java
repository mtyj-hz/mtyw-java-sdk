package com.mtyw.storage.model.response.filecoin;

/**
 * @author lt
 * @Date 15:37 2021/1/15
 */
public class FilecoindownloadRes {
    private String sign;
    private Long timestamp;
    private String nodeIp;
    private String nodeAddr;
    private Integer userId;
    private String rootCid;
    private String fileName;

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

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    public String getNodeAddr() {
        return nodeAddr;
    }

    public void setNodeAddr(String nodeAddr) {
        this.nodeAddr = nodeAddr;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRootCid() {
        return rootCid;
    }

    public void setRootCid(String rootCid) {
        this.rootCid = rootCid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

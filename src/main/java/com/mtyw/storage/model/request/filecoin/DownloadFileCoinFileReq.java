package com.mtyw.storage.model.request.filecoin;

import com.mtyw.storage.model.response.filecoin.FilecoindownloadRes;

/**
 * @author lt
 * @Date 11:44 2021/1/6
 */
public class DownloadFileCoinFileReq {

    private Integer userId;
    private String nodeIp;
    private String fileName;
    private String rootCid;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRootCid() {
        return rootCid;
    }

    public void setRootCid(String rootCid) {
        this.rootCid = rootCid;
    }

    public DownloadFileCoinFileReq(FilecoindownloadRes r) {
        this.sign = r.getSign();
        this.timestamp = r.getTimestamp();
        this.userId = r.getUserId();
        this.nodeIp = r.getNodeIp();
        this.fileName = r.getFileName();
        this.rootCid = r.getRootCid();
    }
}

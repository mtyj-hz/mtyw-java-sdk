package com.mtyw.storage.model.request.ipfs;

import com.mtyw.storage.model.response.ipfs.FileDownloadResponse;

/**
 * @author lt
 * @Date 11:44 2021/1/6
 */
public class DownloadIpfsFileReq {

    private Integer userid;
    private String nodeip;
    private String filename;
    private String cid;
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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getNodeip() {
        return nodeip;
    }

    public void setNodeip(String nodeip) {
        this.nodeip = nodeip;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public DownloadIpfsFileReq(FileDownloadResponse r) {
        this.sign = r.getSign();
        this.timestamp = r.getTimestamp();
        this.userid = r.getUid();
        this.nodeip = r.getNodeip();
        this.filename = r.getFilename();
        this.cid = r.getCid();
    }
}

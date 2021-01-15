package com.mtyw.storage.model.request.filecoin;

import com.mtyw.storage.model.response.filecoin.FilecoindownloadRes;
import com.mtyw.storage.model.response.ipfs.FileDownloadResponse;

/**
 * @author lt
 * @Date 11:44 2021/1/6
 */
public class DownloadFileCoinFileReq {

    private Integer userid;
    private String nodeip;
    private String filename;
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

    public String getRootCid() {
        return rootCid;
    }

    public void setRootCid(String rootCid) {
        this.rootCid = rootCid;
    }

    public DownloadFileCoinFileReq(FilecoindownloadRes r) {
        this.sign = r.getSign();
        this.timestamp = r.getTimestamp();
        this.userid = r.getUserId();
        this.nodeip = r.getNodeIp();
        this.filename = r.getFileName();
        this.rootCid = r.getRootCid();
    }
}

package com.mtyw.storage.model.request.ipfs;

import com.mtyw.storage.model.response.ipfs.FileDownloadResponse;

/**
 * @author lt
 * @Date 11:44 2021/1/6
 */
public class DownloadIpfsFileRequest extends BaseRequest{

    private Integer userId;
    private String nodeIp;
    private String fileName;
    private String cid;

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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public DownloadIpfsFileRequest(FileDownloadResponse r) {
        super(r.getSign(), r.getTimestamp());
        this.userId = r.getUid();
        this.nodeIp = r.getNodeip();
        this.fileName = r.getFilename();
        this.cid = r.getCid();
    }
}

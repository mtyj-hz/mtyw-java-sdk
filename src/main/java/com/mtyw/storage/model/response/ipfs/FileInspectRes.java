package com.mtyw.storage.model.response.ipfs;

/**
 * @author lt
 * @Date 11:13 2021/1/7
 */
public class FileInspectRes {

    private String sign;
    private String nodeip;
    private String nodeAddr;
    private String cid;
    private Long timestamp;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNodeip() {
        return nodeip;
    }

    public void setNodeip(String nodeip) {
        this.nodeip = nodeip;
    }

    public String getNodeAddr() {
        return nodeAddr;
    }

    public void setNodeAddr(String nodeAddr) {
        this.nodeAddr = nodeAddr;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "FileInspectRes{" +
                "sign='" + sign + '\'' +
                ", nodeip='" + nodeip + '\'' +
                ", nodeAddr='" + nodeAddr + '\'' +
                ", cid='" + cid + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

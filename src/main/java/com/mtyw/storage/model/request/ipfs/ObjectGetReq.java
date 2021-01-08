package com.mtyw.storage.model.request.ipfs;

/**
 * @author lt
 * @Date 11:02 2021/1/8
 */
public class ObjectGetReq {
    private Long timestamp;
    private String sign;
    private String cid;
    private String nodeip;

    public ObjectGetReq(String nodeip, String cid, String sign, Long timestamp) {
        this.nodeip = nodeip;
        this.cid = cid;
        this.sign = sign;
        this.timestamp = timestamp;
    }

    public String getNodeip() {
        return nodeip;
    }

    public void setNodeip(String nodeip) {
        this.nodeip = nodeip;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

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

    @Override
    public String toString() {
        return "ObjectGetReq{" +
                "nodeip='" + nodeip + '\'' +
                ", cid='" + cid + '\'' +
                ", sign='" + sign + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

package com.mtyw.storage.model.response.ipfs;

import com.mtyw.storage.model.response.ResultResponse;

/**
 * @Author: xiaoli
 * @Date: 2020/12/31 5:18 下午
 */
public class UploadipfsSignDTO extends ResultResponse<UploadipfsSignDTO> {

    private String sign;
    private Long timestamp;
    private Long expiretime;
    private String nodeAddr;
    private String filepath;
    private String nodeIp;
    private Integer uploadid;
    private Integer userid;
    private String regionid;
    private String filename;
    private Long filesize;

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

    public Long getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(Long expiretime) {
        this.expiretime = expiretime;
    }

    public String getNodeAddr() {
        return nodeAddr;
    }

    public void setNodeAddr(String nodeAddr) {
        this.nodeAddr = nodeAddr;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    public Integer getUploadid() {
        return uploadid;
    }

    public void setUploadid(Integer uploadid) {
        this.uploadid = uploadid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getRegionid() {
        return regionid;
    }

    public void setRegionid(String regionid) {
        this.regionid = regionid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }
}

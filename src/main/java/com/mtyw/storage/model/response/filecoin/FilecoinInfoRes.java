package com.mtyw.storage.model.response.filecoin;


import java.io.Serializable;
import java.util.Date;


public class FilecoinInfoRes implements Serializable {

    private static final long serialVersionUID=1L;
    /**
     * id信息
     */
    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 类型0ipfs，1filecoin
     */
    private Integer type;

    /**
     * 文件路径
     */
    private String path;
    /**
     * 用户id
     */
    private Integer uid;

    /**
     * hash
     */
    private String cid;

    /**
     * 节点id
     */
    private String nodeId;

    /**
     * 0正在上传，1可下载，2上传失败，3上传超时,4 做单进行中，5待检索,6检索中,7检索失败
     */
    private Integer status;

    /**
     * 过期时间
     */
    private Long expireTime;
    /**
     * 天数信息
     */
    private Integer days;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}

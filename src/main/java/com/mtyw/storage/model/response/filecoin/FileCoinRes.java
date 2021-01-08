package com.mtyw.storage.model.response.filecoin;


public class FileCoinRes {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * cid信息
     */
    private String cid;
    /**
     * 节点id
     */
    private String nodeid;
    /**
     * 创建时间
     */
    private Long timestamp;
    /**
     * 可用天数
     */
    private Long useableDay;
    /**
     * 0正在上传，1可下载，2上传失败，3上传超时,4 做单进行中，5待检索,6检索中,7检索失败
     */
    private Integer status;
    /**
     * 上传id信息
     */
    private Integer id;

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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUseableDay() {
        return useableDay;
    }

    public void setUseableDay(Long useableDay) {
        this.useableDay = useableDay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

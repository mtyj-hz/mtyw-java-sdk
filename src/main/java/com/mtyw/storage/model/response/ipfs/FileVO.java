package com.mtyw.storage.model.response.ipfs;

import java.util.List;

/**
 * @author lt
 * @Date 11:19 2021/1/7
 */
public class FileVO {
    private String fileName;
    private Long fileSize;
    private String cid;
    private List<Node> nodeids;
    private Long timestamp;
    private String path;
    private Integer type;

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

    public List<Node> getNodeids() {
        return nodeids;
    }

    public void setNodeids(List<Node> nodeids) {
        this.nodeids = nodeids;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public static class Node{
        private String nodeid;
        private String regionName;
        private Integer regionId;

        public String getNodeid() {
            return nodeid;
        }

        public void setNodeid(String nodeid) {
            this.nodeid = nodeid;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public Integer getRegionId() {
            return regionId;
        }

        public void setRegionId(Integer regionId) {
            this.regionId = regionId;
        }
    }
}
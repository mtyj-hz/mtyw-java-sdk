package com.mtyw.storage.model.response.ipfs;

import java.util.List;

/**
 * @author lt
 * @Date 11:16 2021/1/7
 */
public class FileDetailRes {

    private String fileName;
    private Long fileSize;
    private String cid;
    private List<Node> nodeids;
    private String path;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

        @Override
        public String toString() {
            return "Node{" +
                    "nodeid='" + nodeid + '\'' +
                    ", regionName='" + regionName + '\'' +
                    ", regionId=" + regionId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FileDetailRes{" +
                "fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", cid='" + cid + '\'' +
                ", nodeids=" + nodeids +
                ", path='" + path + '\'' +
                '}';
    }
}

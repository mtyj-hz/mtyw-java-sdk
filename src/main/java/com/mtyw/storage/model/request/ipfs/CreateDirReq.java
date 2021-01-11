package com.mtyw.storage.model.request.ipfs;


public class CreateDirReq {
    /**
     * 父级目录path
     */
    private String parentpath;
    /**
     * 需要在父级目录创建的 文件名
     */
    private String directoryName;

    public String getParentpath() {
        return parentpath;
    }

    public void setParentpath(String parentpath) {
        this.parentpath = parentpath;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }
}

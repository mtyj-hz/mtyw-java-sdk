package com.mtyw.storage.model.response.ipfs;

import java.util.List;

/**
 * @author lt
 * @Date 11:19 2021/1/7
 */
public class FileInfoRes {
    private List<FileVO> fileVOS;

    /**
     * 父路径
     */
    private String parentPath;

    public List<FileVO> getFileVOS() {
        return fileVOS;
    }

    public void setFileVOS(List<FileVO> fileVOS) {
        this.fileVOS = fileVOS;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    @Override
    public String toString() {
        return "FileInfoRes{" +
                "fileVOS=" + fileVOS +
                ", parentPath='" + parentPath + '\'' +
                '}';
    }
}

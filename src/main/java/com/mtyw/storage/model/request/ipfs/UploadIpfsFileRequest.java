package com.mtyw.storage.model.request.ipfs;


import java.io.InputStream;

public class UploadIpfsFileRequest {
    /**
     * 文件流
     */
    private InputStream inputStream;
    /**
     * 文件名
     */
    private String fileName;

    private String filepath;
    /**
     * 文件大小，单位b
     */
    private Long fileSize;
    /**
     * 机房id，由获取机房列表接口返回@lINK
     */
    private String regionId;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
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

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}

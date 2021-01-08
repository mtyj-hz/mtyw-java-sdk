package com.mtyw.storage.model.request.ipfs;


import com.mtyw.storage.annotation.Null;

import java.io.InputStream;

public class UploadIpfsFileReq {
    /**
     * 文件流
     */
    private InputStream inputStream;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小，单位b
     */
    private Long fileSize;
    /**
     * 文件路径
     */
    private String filepath;
    /**
     * 区域信息
     */
    private String regionId;
    /**
     * 断点续传上传请求id，由上传服务器回调返回，第一次上传可不传，传了代表断点续传
     */
    @Null
    private Long uploadRequestId;


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

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public Long getUploadRequestId() {
        return uploadRequestId;
    }

    public void setUploadRequestId(Long uploadRequestId) {
        this.uploadRequestId = uploadRequestId;
    }

}

package com.mtyw.storage.model.request.ipfs;


import com.mtyw.storage.annotation.Null;

import java.io.FileInputStream;

public class UploadIpfsFileReq {
    /**
     * 文件流
     */
    private FileInputStream inputStream;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小，单位b
     */
    private Long fileSize;
    /**
     * 文件路径 /a.txt ，如果要在某个文件夹下创建文件，需要先调用创建文件夹接口，再在这个文件夹下创建文件
     */
    private String filepath;
    /**
     * 区域信息
     */
    private String regionId;
    /**
     * 断点续传上传请求id，由上传时服务器回调返回{@link com.mtyw.storage.common.CallBack},当需要续传时带上id，第一次上传可不传，传了才代表是断点续传
     */
    @Null
    private Long uploadRequestId;


    public FileInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(FileInputStream inputStream) {
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

package com.mtyw.storage.model.request.filecoin;


import com.mtyw.storage.annotation.Null;
import com.mtyw.storage.model.response.filecoin.FilecoinDateRes;
import com.mtyw.storage.model.response.filecoin.NodeRes;

import java.io.FileInputStream;

public class UploadFileCoinFileReq {
    /**
     * 文件流
     */
    private FileInputStream inputStream;
    /**
     * 文件名
     */
    private String fileName;
    /**
     *  filecoin节点id，由获取上传节点返回{@link NodeRes},MFSS.getFilecoinNodelist 接口返回,
     */
    private String nodeId;
    /**
     * 文件大小，单位b
     */
    private Long fileSize;
    /**
     * 天数信息 返回{@link FilecoinDateRes} MFSS.filecoinDatelist 接口返回,
     */
    private Integer days;

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

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Long getUploadRequestId() {
        return uploadRequestId;
    }

    public void setUploadRequestId(Long uploadRequestId) {
        this.uploadRequestId = uploadRequestId;
    }
}

package com.mtyw.storage.model.request.filecoin;


import java.io.InputStream;
import java.math.BigDecimal;

public class UploadFileCoinFileRequest {
    /**
     * 文件流
     */
    private InputStream inputStream;
    /**
     * 文件名
     */
    private String fileName;

    private String nodeId;
    /**
     * 文件大小，单位b
     */
    private Long fileSize;
    /**
     * 天数信息
     */
    private Integer days;

    private BigDecimal unitPrice;
    private BigDecimal minPrice;

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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }
}

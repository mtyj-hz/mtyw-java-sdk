package com.mtyw.storage.model.request.filecoin;


import java.math.BigDecimal;


public class RetrieveReq {
    /**
     * 文件cid信息
     */
    private String cid;
    /**
     * 文件大小
     */
    private Long size;
    /**
     * 上传uploadid
     */
    private Integer uploadId;
    /**
     * 单价
     */
    private BigDecimal unitPrice;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getUploadId() {
        return uploadId;
    }

    public void setUploadId(Integer uploadId) {
        this.uploadId = uploadId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}

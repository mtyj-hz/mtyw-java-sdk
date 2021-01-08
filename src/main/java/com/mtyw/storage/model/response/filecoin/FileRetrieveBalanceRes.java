package com.mtyw.storage.model.response.filecoin;


import java.math.BigDecimal;


public class FileRetrieveBalanceRes {
    /**
     * 是否可用
     */
    private Boolean useable;
    /**
     * 检索价格
     */
    private BigDecimal price;
    /**
     * 检索单价
     */
    private BigDecimal unitPrice;

    public Boolean getUseable() {
        return useable;
    }

    public void setUseable(Boolean useable) {
        this.useable = useable;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

}

package com.mtyw.storage.model.response.filecoin;


import java.math.BigDecimal;


public class FileBalanceRes {
    /**
     * 是否可用, 不可用需要充值
     */
    private Boolean useable;
    /**
     * 计算出来的价格
     */
    private BigDecimal price;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 最小价
     */
    private BigDecimal minPrice;

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

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }
}

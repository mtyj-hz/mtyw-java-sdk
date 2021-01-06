package com.mtyw.storage.model.response.filecoin;


import java.math.BigDecimal;


public class FileRetrieveBalanceRes {
    private Boolean useable;
    private BigDecimal price;
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

package com.mtyw.storage.model.response.filecoin;

import java.math.BigDecimal;


public class FilecoinDateRes {

    /**
     * 内容ID
     */
    private Integer specId;

    /**
     * 内容
     */
    private String data;
    /**
     * 单位
     */
    private String unit;
    /**
     * 折扣
     */
    private BigDecimal discount;

    private String tag;
    /**
     * 备注
     */
    private String args;
    /**
     * 天数
     */
    private Integer days;


    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}



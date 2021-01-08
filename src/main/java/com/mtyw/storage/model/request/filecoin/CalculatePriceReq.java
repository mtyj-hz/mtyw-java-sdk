package com.mtyw.storage.model.request.filecoin;


import java.util.List;


public class CalculatePriceReq {
    /**
     * 待计算价格待文件size列表
     */
    private List<Long> sizeList;
    /**
     * 天数
     */
    private Integer days;

    public List<Long> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<Long> sizeList) {
        this.sizeList = sizeList;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}

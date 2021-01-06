package com.mtyw.storage.model.request.filecoin;


import java.util.List;


public class CalculatePriceReq {
    private List<Long> sizeList;
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

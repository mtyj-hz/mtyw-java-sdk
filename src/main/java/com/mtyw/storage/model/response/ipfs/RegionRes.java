package com.mtyw.storage.model.response.ipfs;

/**
 * @author lt
 * @Date 11:15 2021/1/7
 */
public class RegionRes {

    /**
     * 机房名称
     */
    private String regionName;

    /**
     * 机房id
     */
    private Integer regioneId;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getRegioneId() {
        return regioneId;
    }

    public void setRegioneId(Integer regioneId) {
        this.regioneId = regioneId;
    }

    @Override
    public String toString() {
        return "RegionRes{" +
                "regionName='" + regionName + '\'' +
                ", regioneId=" + regioneId +
                '}';
    }
}

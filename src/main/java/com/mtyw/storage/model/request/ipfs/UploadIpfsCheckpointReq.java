package com.mtyw.storage.model.request.ipfs;


public class UploadIpfsCheckpointReq {

    /**
     * 文件路径
     */
    private String filepath;
    /**
     *  上传id
     */
    private Integer uploadid;

    private Integer userid;

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Integer getUploadid() {
        return uploadid;
    }

    public void setUploadid(Integer uploadid) {
        this.uploadid = uploadid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}

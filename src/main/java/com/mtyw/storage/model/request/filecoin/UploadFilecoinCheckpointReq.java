package com.mtyw.storage.model.request.filecoin;


public class UploadFilecoinCheckpointReq {

    /**
     * 文件路径
     */
    private String filename;
    /**
     *  上传id
     */
    private Integer uploadid;
    /**
     *用户id
     */
    private Integer userid;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

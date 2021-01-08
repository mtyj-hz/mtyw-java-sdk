package com.mtyw.storage.model.request.ipfs;


public class UploadCallbackReq {
    /**
     * 上传id ,回调会返回 断点续传需要用到该id
     */
    private Integer uploadid;
    /**
     *   cid, 非必须
     */
    private String cid;
    /**
     * 文件路径,id回调没有该信息
     */
    private String filepath;
    /**
     * 是否成功
     */
    private Boolean success;
    /**
     * 失败消息, 非必须，成功就没有
     */
    private String msg;

    public Integer getUploadid() {
        return uploadid;
    }

    public void setUploadid(Integer uploadid) {
        this.uploadid = uploadid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

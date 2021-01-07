package com.mtyw.storage.model.request.ipfs;

/**
 * @author lt
 * @Date 16:29 2021/1/6
 */
public class DownloadIpfsFileSignRequest {
    private String filepath;

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public DownloadIpfsFileSignRequest(String filepath) {
        this.filepath = filepath;
    }
}

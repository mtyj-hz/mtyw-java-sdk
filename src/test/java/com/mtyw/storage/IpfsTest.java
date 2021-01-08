package com.mtyw.storage;

import com.mtyw.storage.model.request.ipfs.UploadIpfsFileReq;
import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.model.response.ipfs.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author lt
 * @Date 15:43 2021/1/7
 */
public class IpfsTest extends TestBase{

    String filepath = "/8A90EE80-CE9D-47D1-A0C8-0D1C1DD1621B.jpeg";
    String filename = "8A90EE80-CE9D-47D1-A0C8-0D1C1DD1621B.jpeg";
    String regionId = "10087";

    @Test
    public  void ipfsInspectsign() {
        ResultResponse<ObjectGetRes> resultResponse = mfssClient.objectGet(filepath);
        System.out.println(resultResponse);
    }

    @Test
    public  void getAllRegionList() {
        ResultResponse<List<RegionRes>> allRegionList = mfssClient.getAllRegionList();
        System.out.println(allRegionList);
    }

    @Test
    public  void backupManagement() {
        ResultResponse<FileDetailRes> param = mfssClient.backupManagement(filepath);
        System.out.println(param);
    }

    @Test
    public  void getUsableRegionList() {
        ResultResponse<List<RegionRes>> param = mfssClient.getUsableRegionList(100L);
        System.out.println(param);
    }

    @Test
    public  void searchIpfsDirectorylist() {
        ResultResponse<FileInfoRes> param = mfssClient.searchIpfsDirectorylist(filepath, filename, regionId);
        System.out.println(param);
    }

    @Test
    public  void getIpfsDirectorylist() {
        ResultResponse<FileInfoRes> param = mfssClient.getIpfsDirectorylist(filepath);
        System.out.println(param);
    }

    @Test
    public  void deleteIpfsfile() {
        ResultResponse<Boolean> param = mfssClient.deleteIpfsfile(filepath, Collections.singletonList("1"));
        System.out.println(param);
    }

    @Test
    public  void deleteIpfsfileList() {
        ResultResponse<Boolean> param = mfssClient.deleteIpfsfileList(Collections.singletonList(filepath));
        System.out.println(param);
    }

    @Test
    public  void movefileToDirectory() {
        ResultResponse<Boolean> param = mfssClient.movefileToDirectory("/test", filepath);
        System.out.println(param);
    }

    @Test
    public  void renameDirectory() {
        ResultResponse<Boolean> param = mfssClient.renameDirectory("/test", "/test2");
        System.out.println(param);
    }

    @Test
    public  void uploadIpfsfile() {
        UploadIpfsFileReq uploadIpfsFileRequest = new UploadIpfsFileReq();
        uploadIpfsFileRequest.setFileName("uzinvitation_1592882753457.jpg");
        InputStream inputStream = null;
        Long length = 0l;
        try{
            inputStream = new FileInputStream("/Users/chenxiaoli/uzinvitation_1592882753457.jpg");
            length = Long.valueOf(inputStream.available());
        }catch (Exception e) {

        }
        uploadIpfsFileRequest.setFileSize(length);
        //uploadIpfsFileRequest.setUploadRequestId(1738l);
        uploadIpfsFileRequest.setInputStream(inputStream);
        uploadIpfsFileRequest.setFilepath("/uzinvitation_1592882753457.jpg");
        uploadIpfsFileRequest.setRegionId("10087");
        ResultResponse resultResponse = mfssClient.uploadIpfsFile(uploadIpfsFileRequest,null, null);
        int aa =1;
        return ;
    }

}

package com.mtyw.storage;

import com.mtyw.storage.model.request.filecoin.CalculatePriceReq;
import com.mtyw.storage.model.request.filecoin.FilecoinInfoReq;
import com.mtyw.storage.model.request.filecoin.RetrieveReq;
import com.mtyw.storage.model.request.filecoin.UploadFileCoinFileReq;
import com.mtyw.storage.model.request.ipfs.UploadIpfsFileReq;
import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.model.response.filecoin.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: xiaoli
 * @Date: 2021/1/5 4:15 下午
 */
public class FilecoinTest extends TestBase{

    @Test
    public  void filecoinDatelist() {
        ResultResponse<List<FilecoinDateRes>> resultResponse = mfssClient.filecoinDatelist();
        int aa =1;
        return ;
    }
    @Test
    public  void calculatePrice() {
        CalculatePriceReq calculatePriceReq = new CalculatePriceReq();
        calculatePriceReq.setDays(180);
        calculatePriceReq.setSizeList(Arrays.asList(10000l));
        ResultResponse<FileBalanceRes> resultResponse = mfssClient.calculatePrice(calculatePriceReq);
        return ;
    }

    @Test
    public  void uploadFilecoinfile() {
        UploadFileCoinFileReq uploadIpfsFileRequest = new UploadFileCoinFileReq();
        uploadIpfsFileRequest.setFileName("qyn.mp4");
        FileInputStream inputStream = null;
        Long length = 0l;
        try{
            inputStream = new FileInputStream("/Users/chenxiaoli/qyn.mp4");
            length = Long.valueOf(inputStream.available());
        }catch (Exception e) {

        }
        uploadIpfsFileRequest.setFileSize(length);
        uploadIpfsFileRequest.setDays(180);
        uploadIpfsFileRequest.setNodeId("aa");
        uploadIpfsFileRequest.setUploadRequestId(1754l);
        //uploadIpfsFileRequest.setUploadRequestId(1738l);
        uploadIpfsFileRequest.setInputStream(inputStream);
        ResultResponse resultResponse = mfssClient.uploadFilecoinFile(uploadIpfsFileRequest,null, null);
        int aa =1;
        return ;
    }

    @Test
    public  void calculateRetrievePrice() {

        ResultResponse<FileRetrieveBalanceRes> resultResponse = mfssClient.calculateRetrievePrice(10000l);
        return ;
    }

    @Test
    public  void getFilecoinNodelist() {
        ResultResponse<List<NodeRes>> resultResponse = mfssClient.getFilecoinNodelist();
        return ;
    }
    @Test
    public  void getFilecoinDirectorylist() {
        ResultResponse<List<FileCoinRes>> resultResponse = mfssClient.getFilecoinDirectorylist(1,100);
        return ;
    }
    @Test
    public  void retrieve() {
        RetrieveReq retrieveReq = new RetrieveReq();
        retrieveReq.setCid("bafk2bzacedsd24iake6d6uy2btivhubwvovaef7wcwwdkmnbwfvwg6hxl7nwe");
        retrieveReq.setSize(3153l);
        retrieveReq.setUnitPrice(BigDecimal.valueOf(1));
        retrieveReq.setUploadId(1733);
        ResultResponse<Boolean> resultResponse = mfssClient.retrieve(retrieveReq);
        return ;
    }
    @Test
    public  void fileInfo() {
        FilecoinInfoReq filecoinInfoReq = new FilecoinInfoReq();
        filecoinInfoReq.setUploadId(1733);
        ResultResponse<FilecoinInfoRes> resultResponse = mfssClient.fileInfo(filecoinInfoReq);
        return ;
    }


}

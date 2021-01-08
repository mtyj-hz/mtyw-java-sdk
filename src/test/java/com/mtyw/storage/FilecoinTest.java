package com.mtyw.storage;

import com.mtyw.storage.model.request.filecoin.CalculatePriceReq;
import com.mtyw.storage.model.request.filecoin.UploadFileCoinFileReq;
import com.mtyw.storage.model.request.ipfs.UploadIpfsFileReq;
import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.model.response.filecoin.FileBalanceRes;
import com.mtyw.storage.model.response.filecoin.FilecoinDateRes;
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
    public  void uploadfile() {
        UploadFileCoinFileReq uploadIpfsFileRequest = new UploadFileCoinFileReq();
        uploadIpfsFileRequest.setFileName("sdk文件.txt");
        InputStream inputStream = null;
        Long length = 0l;
        try{
            inputStream = new FileInputStream("/Users/chenxiaoli/jihuo.txt");
            length = Long.valueOf(inputStream.available());
        }catch (Exception e) {

        }
        uploadIpfsFileRequest.setFileSize(length);

        uploadIpfsFileRequest.setInputStream(inputStream);
        uploadIpfsFileRequest.setDays(180);
        uploadIpfsFileRequest.setUnitPrice(BigDecimal.valueOf(0.0002275));
        uploadIpfsFileRequest.setMinPrice(BigDecimal.valueOf(1));
        uploadIpfsFileRequest.setNodeId("rere");


        ResultResponse resultResponse = mfssClient.uploadFilecoinFile(uploadIpfsFileRequest,null);
        int aa =1;
        return ;
    }

    @Test
    public  void uploadIpfsfile() {
        UploadIpfsFileReq uploadIpfsFileRequest = new UploadIpfsFileReq();
        uploadIpfsFileRequest.setFileName("sdk文件.txt");
        InputStream inputStream = null;
        Long length = 0l;
        try{
            inputStream = new FileInputStream("/Users/chenxiaoli/Downloads/竞争力能力实力对比PPT模板.pptx");
            length = Long.valueOf(inputStream.available());
        }catch (Exception e) {

        }
        uploadIpfsFileRequest.setFileSize(length);

        uploadIpfsFileRequest.setInputStream(inputStream);
        uploadIpfsFileRequest.setFileName("竞争力能力实力对比PPT模板.pptx");
        uploadIpfsFileRequest.setFilepath("/竞争力能力实力对比PPT模板.pptx");
        uploadIpfsFileRequest.setRegionId("10087");

        ResultResponse resultResponse = mfssClient.uploadIpfsFile(uploadIpfsFileRequest,null, null);
        int aa =1;
        return ;
    }
}

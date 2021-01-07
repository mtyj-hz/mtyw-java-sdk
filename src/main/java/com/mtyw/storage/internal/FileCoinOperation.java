package com.mtyw.storage.internal;


import com.mtyw.storage.HttpMethod;
import com.mtyw.storage.common.CallBack;
import com.mtyw.storage.common.MFSSRequestBuilder;
import com.mtyw.storage.common.Request;
import com.mtyw.storage.common.ServiceClient;
import com.mtyw.storage.constant.ResourePathConstant;
import com.mtyw.storage.exception.MtywApiException;
import com.mtyw.storage.model.request.filecoin.CalculatePriceReq;
import com.mtyw.storage.model.request.filecoin.RetrieveReq;
import com.mtyw.storage.model.request.filecoin.UploadFileCoinFileReq;
import com.mtyw.storage.model.request.filecoin.UploadFilecoinReq;
import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.model.response.filecoin.FileBalanceRes;
import com.mtyw.storage.model.response.filecoin.FileRetrieveBalanceRes;
import com.mtyw.storage.model.response.filecoin.FilecoinDateRes;
import com.mtyw.storage.model.response.filecoin.UploadFilecoinSignDTO;
import com.mtyw.storage.util.HttpHeaders;

import java.util.List;

import static com.mtyw.storage.constant.MFSSConstants.*;

public class FileCoinOperation extends FileCommonOperation {
    public FileCoinOperation(ServiceClient client , String accesskey, String accesssecret) {
        super(client, accesskey,accesssecret);
    }


    public ResultResponse uploadFilecoinFile(UploadFileCoinFileReq uploadFileCoinFileReq, CallBack callBack) throws MtywApiException {
        Request request = new MFSSRequestBuilder<>(uploadFileCoinFileReq,false).build();
        request.setResourcePath(ResourePathConstant.UPLOADFILECOINSIGN_RESOURCE);
        ResultResponse<UploadFilecoinSignDTO> uploadFilecoinSignDTO = commonParserExecute(request, UploadFilecoinSignDTO.class);
        if (uploadFilecoinSignDTO.isSuccess()) {
            UploadFilecoinReq uploadFilecoinReq = new UploadFilecoinReq();
            uploadFilecoinReq.setNodeAddr(uploadFilecoinSignDTO.getData().getNodeAddr());
            uploadFilecoinReq.setNodeIp(uploadFilecoinSignDTO.getData().getNodeIp());
            uploadFilecoinReq.setSign(uploadFilecoinSignDTO.getData().getSign());
            uploadFilecoinReq.setTimestamp(uploadFilecoinSignDTO.getData().getTimestamp());
            uploadFilecoinReq.setDays(uploadFilecoinSignDTO.getData().getDays());
            uploadFilecoinReq.setFileName(uploadFilecoinSignDTO.getData().getFileName());
            uploadFilecoinReq.setFileSize(uploadFilecoinSignDTO.getData().getFileSize());
            uploadFilecoinReq.setUploadId(uploadFilecoinSignDTO.getData().getUploadId());
            uploadFilecoinReq.setUserId(uploadFilecoinSignDTO.getData().getUserId());
            Request uploadFilecoinSignRequest = new MFSSRequestBuilder<>(uploadFilecoinReq,false).build();
            uploadFilecoinSignRequest.setResourcePath(ResourePathConstant.UPLOADFILECOIN_RESOURCE);
            uploadFilecoinSignRequest.addHeader(HttpHeaders.CONTENT_TYPE, DEFAULT_MULTIPART_CONTENT_TYPE);
            uploadFilecoinSignRequest.addHeader(HttpHeaders.CONNECTION, HTTP_OBJECT_CONNECTION);
            uploadFilecoinSignRequest.setMethod(HttpMethod.POST);
            uploadFilecoinSignRequest.setContentLength(uploadFileCoinFileReq.getFileSize());
            uploadFilecoinSignRequest.setContent(uploadFileCoinFileReq.getInputStream());
            uploadFilecoinSignRequest.setUrl(uploadFilecoinSignDTO.getData().getNodeAddr());
            ResultResponse<String> resultResponse = commonParserExecute(uploadFilecoinSignRequest, String.class);
            if (callBack != null) {
                try{
                    callBack.invoke(callBack.getArgs());
                }catch (Exception e) {

                }
            }
        }
        return ResultResponse.suc();
    }

    public ResultResponse<List<FilecoinDateRes>> filecoinDatelist(){
        Request request = new MFSSRequestBuilder<>().build();
        request.setResourcePath(ResourePathConstant.FILECOIN_DATELIST_RESOURCE);

        ResultResponse<List<FilecoinDateRes>> resultResponse = commonParserExcutelist(request, FilecoinDateRes.class);
        return resultResponse;

    }

    public ResultResponse<FileBalanceRes> calculatePrice(CalculatePriceReq calculatePriceReq) {
        Request request = new MFSSRequestBuilder<>(calculatePriceReq, true).build();
        request.setResourcePath(ResourePathConstant.FILECOIN_CALCULATE_PRICE_RESOURCE);
        request.addHeader(HttpHeaders.CONTENT_TYPE, DEFAULT_OBJECT_CONTENT_TYPE);
        request.setMethod(HttpMethod.POST);
        try{
            int length = request.getContent().available();
            request.setContentLength(length);
        }catch (Exception e){
        }
        ResultResponse<FileBalanceRes> resultResponse = commonParserExcute(request, FileBalanceRes.class);
        return resultResponse;
    }

    public ResultResponse<FileRetrieveBalanceRes> calculateRetrievePrice(Long size){
        Request request = new MFSSRequestBuilder<>().build();
        request.addParameter("size",size.toString());
        request.setResourcePath(ResourePathConstant.FILECOIN_RETRIEVE_CALCULATE_PRICE_RESOURCE);
        ResultResponse<FileRetrieveBalanceRes> resultResponse = commonParserExcute(request, FileRetrieveBalanceRes.class);
        return resultResponse;
    }

    public ResultResponse<Boolean> retrieve(RetrieveReq retrieveReq){
        Request request = new MFSSRequestBuilder<>(retrieveReq,false).build();
        request.setResourcePath(ResourePathConstant.FILECOIN_RETRIEVE_RESOURCE);
        ResultResponse<Boolean> resultResponse = commonParserExcute(request, Boolean.class);
        return resultResponse;
    }
}

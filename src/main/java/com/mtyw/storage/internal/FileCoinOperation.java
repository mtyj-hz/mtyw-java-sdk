package com.mtyw.storage.internal;


import com.mtyw.storage.HttpMethod;
import com.mtyw.storage.common.CallBack;
import com.mtyw.storage.common.MFSSRequestBuilder;
import com.mtyw.storage.common.Request;
import com.mtyw.storage.common.ServiceClient;
import com.mtyw.storage.constant.ResourePathConstant;
import com.mtyw.storage.exception.MtywApiException;
import com.mtyw.storage.model.request.filecoin.*;
import com.mtyw.storage.model.request.ipfs.UploadCallbackReq;
import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.model.response.filecoin.*;
import com.mtyw.storage.util.HttpHeaders;

import java.util.List;

import static com.mtyw.storage.constant.MFSSConstants.*;

public class FileCoinOperation extends FileCommonOperation {
    public FileCoinOperation(ServiceClient client, String accesskey, String accesssecret) {
        super(client, accesskey, accesssecret);
    }


    public ResultResponse<String> uploadFilecoinFile(UploadFileCoinFileReq uploadFileCoinFileReq, CallBack callBackReceiveRequestid, CallBack callBackFinish) throws MtywApiException {
        Request request = new MFSSRequestBuilder<>(uploadFileCoinFileReq, false).build();
        request.setResourcePath(ResourePathConstant.UPLOADFILECOINSIGN_RESOURCE);
        ResultResponse<UploadFilecoinSignDTO> uploadFilecoinSignDTO = commonParserExcute(request, UploadFilecoinSignDTO.class);
        if (uploadFilecoinSignDTO.isSuccess()) {
            if (callBackReceiveRequestid != null) {
                try {
                    UploadCallbackReq uploadCallbackReq = new UploadCallbackReq();
                    uploadCallbackReq.setUploadid(uploadFilecoinSignDTO.getData().getUploadId());
                    uploadCallbackReq.setSuccess(true);
                    callBackFinish.invoke(uploadCallbackReq);
                } catch (Exception e) {

                }
            }
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
            Request uploadFilecoinSignReq = new MFSSRequestBuilder<>(uploadFilecoinReq, false).build();
            uploadFilecoinSignReq.setResourcePath(ResourePathConstant.UPLOADFILECOIN_RESOURCE);
            uploadFilecoinSignReq.addHeader(HttpHeaders.CONTENT_TYPE, DEFAULT_MULTIPART_CONTENT_TYPE);
            uploadFilecoinSignReq.addHeader(HttpHeaders.CONNECTION, HTTP_OBJECT_CONNECTION);
            uploadFilecoinSignReq.setMethod(HttpMethod.POST);
            uploadFilecoinSignReq.setContentLength(uploadFileCoinFileReq.getFileSize());
            uploadFilecoinSignReq.setContent(uploadFileCoinFileReq.getInputStream());
            uploadFilecoinSignReq.setUrl(uploadFilecoinSignDTO.getData().getNodeAddr());
            ResultResponse<String> resultResponse = commonParserExcute(uploadFilecoinSignReq, String.class);
            if (callBackFinish != null) {
                try {
                    UploadCallbackReq uploadCallbackReq = new UploadCallbackReq();
                    uploadCallbackReq.setUploadid(uploadFilecoinSignDTO.getData().getUploadId());
                    uploadCallbackReq.setFilepath(uploadFilecoinSignDTO.getData().getFilepath());
                    uploadCallbackReq.setCid(resultResponse.getData());
                    uploadCallbackReq.setSuccess(true);
                    callBackFinish.invoke(uploadCallbackReq);
                } catch (Exception e) {

                }
            }
            return resultResponse;
        }
        return ResultResponse.fail(uploadFilecoinSignDTO.getCode(), uploadFilecoinSignDTO.getMsg());
    }

    public ResultResponse<List<FilecoinDateRes>> filecoinDatelist() {
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
        try {
            int length = request.getContent().available();
            request.setContentLength(length);
        } catch (Exception e) {
        }
        ResultResponse<FileBalanceRes> resultResponse = commonParserExcute(request, FileBalanceRes.class);
        return resultResponse;
    }

    public ResultResponse<FileRetrieveBalanceRes> calculateRetrievePrice(Long size) {
        Request request = new MFSSRequestBuilder<>().build();
        request.addParameter("size", size.toString());
        request.setResourcePath(ResourePathConstant.FILECOIN_RETRIEVE_CALCULATE_PRICE_RESOURCE);
        ResultResponse<FileRetrieveBalanceRes> resultResponse = commonParserExcute(request, FileRetrieveBalanceRes.class);
        return resultResponse;
    }

    public ResultResponse<Boolean> retrieve(RetrieveReq retrieveReq) {
        Request request = new MFSSRequestBuilder<>(retrieveReq, false).build();
        request.setResourcePath(ResourePathConstant.FILECOIN_RETRIEVE_RESOURCE);
        ResultResponse<Boolean> resultResponse = commonParserExcute(request, Boolean.class);
        return resultResponse;
    }

    public ResultResponse<FilecoinInfoRes> fileInfo(FilecoinInfoReq retrieveReq) {
        Request request = new MFSSRequestBuilder<>(retrieveReq, false).build();
        request.setResourcePath(ResourePathConstant.FILECOIN_FILEINFO_RESOURCE);
        ResultResponse<FilecoinInfoRes> resultResponse = commonParserExcute(request, FilecoinInfoRes.class);
        return resultResponse;
    }

    public ResultResponse<List<NodeRes>> getFilecoinNodelist() {
        Request request = new MFSSRequestBuilder<>().build();
        request.setResourcePath(ResourePathConstant.FILECOIN_NODE_RESOURCE);
        ResultResponse<List<NodeRes>> resultResponse = commonParserExcutelist(request, NodeRes.class);
        return resultResponse;
    }

    public ResultResponse<List<FileCoinRes>> getFilecoinDirectorylist(Integer page, Integer limit) {
        Request request = new MFSSRequestBuilder<>().build();
        request.addParameter("page", page.toString());
        request.addParameter("limit", limit.toString());
        request.setResourcePath(ResourePathConstant.FILECOIN_DIRECTORY_LIST_RESOURCE);
        ResultResponse<List<FileCoinRes>> resultResponse = commonParserExcutelist(request, FileCoinRes.class);
        return resultResponse;
    }
}

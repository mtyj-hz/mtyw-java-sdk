package com.mtyw.storage.internal;


import com.mtyw.storage.HttpMethod;
import com.mtyw.storage.common.CallBack;
import com.mtyw.storage.common.MFSSRequestBuilder;
import com.mtyw.storage.common.Request;
import com.mtyw.storage.common.ServiceClient;
import com.mtyw.storage.constant.ResourePathConstant;
import com.mtyw.storage.exception.MtExceptionEnum;
import com.mtyw.storage.exception.MtywApiException;
import com.mtyw.storage.model.request.filecoin.*;
import com.mtyw.storage.model.request.ipfs.UploadCallbackReq;
import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.model.response.filecoin.*;
import com.mtyw.storage.util.HttpHeaders;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mtyw.storage.constant.MFSSConstants.*;
import static com.mtyw.storage.util.LogUtils.logException;

public class FileCoinOperation extends FileCommonOperation {
    public FileCoinOperation(ServiceClient client, String accesskey, String accesssecret) {
        super(client, accesskey, accesssecret);
    }

    public ResultResponse<String> uploadFilecoinFile(UploadFileCoinFileReq uploadFileCoinFileReq, CallBack callBackReceiveRequestid, CallBack callBackFinish) throws MtywApiException {
        Request request = new MFSSRequestBuilder<>(uploadFileCoinFileReq, false).build();
        long filesize;
        FileChannel channel = uploadFileCoinFileReq.getInputStream().getChannel();
        try {
            filesize = channel.size();
        } catch (IOException e) {
            throw new MtywApiException(MtExceptionEnum.UNKNOWN_ERROR);
        }
        if (!uploadFileCoinFileReq.getFileSize().equals(filesize)) {
            throw new MtywApiException(MtExceptionEnum.FILE_SIZE_ERROR);
        }

        ResultResponse<UploadFilecoinSignDTO> uploadFilecoinSignDTO = commonParserExcute(request, UploadFilecoinSignDTO.class);
        if (uploadFilecoinSignDTO.isSuccess()) {
            if (callBackReceiveRequestid != null) {
                try {
                    UploadCallbackReq uploadCallbackReq = new UploadCallbackReq();
                    uploadCallbackReq.setUploadid(uploadFilecoinSignDTO.getData().getUploadId());
                    uploadCallbackReq.setSuccess(true);
                    callBackFinish.invoke(uploadCallbackReq);
                } catch (Exception e) {
                    logException("Unable to invoke callBackReceiveRequestid error: ", e.getMessage());
                }
            }
            UploadFilecoinReq uploadFilecoinReq = new UploadFilecoinReq();
            uploadFilecoinReq.setFileName(uploadFilecoinSignDTO.getData().getFileName());
            uploadFilecoinReq.setSign(uploadFilecoinSignDTO.getData().getSign());
            uploadFilecoinReq.setTimestamp(uploadFilecoinSignDTO.getData().getTimestamp());
            uploadFilecoinReq.setFileSize(uploadFilecoinSignDTO.getData().getFileSize());
            uploadFilecoinReq.setNodeIp(uploadFilecoinSignDTO.getData().getNodeIp());
            uploadFilecoinReq.setNodeAddr(uploadFilecoinSignDTO.getData().getNodeAddr());
            uploadFilecoinReq.setUploadId(uploadFilecoinSignDTO.getData().getUploadId());
            uploadFilecoinReq.setUserId(uploadFilecoinSignDTO.getData().getUserId());
            uploadFilecoinReq.setDays(uploadFilecoinSignDTO.getData().getDays());
            Request uploadFilecoinAddReq = new MFSSRequestBuilder<>(uploadFilecoinReq, false).build();
            uploadFilecoinAddReq.setContent(uploadFileCoinFileReq.getInputStream());
            uploadFilecoinAddReq.setResourcePath(ResourePathConstant.UPLOAD_FILECOIN_ADD_RESOURCE);
            if (uploadFileCoinFileReq.getUploadRequestId() != null && uploadFileCoinFileReq.getUploadRequestId() > 0) {
                UploadFilecoinCheckpointReq uploadFilecoinCheckpointReq = new UploadFilecoinCheckpointReq();
                uploadFilecoinCheckpointReq.setFilename(uploadFileCoinFileReq.getFileName());
                uploadFilecoinCheckpointReq.setUploadid(uploadFileCoinFileReq.getUploadRequestId().intValue());
                uploadFilecoinCheckpointReq.setUserid(uploadFilecoinSignDTO.getData().getUserId());
                Long checkpoint = getCheckpoint(uploadFilecoinCheckpointReq);
                try {

                    InputStream inputStream = uploadFileCoinFileReq.getInputStream();
                    inputStream.skip(checkpoint);
                    uploadFilecoinAddReq.setContent(inputStream);
                }catch (IOException e) {
                    logException("Unable to readinputstream error: ", e.getMessage());
                }
                uploadFilecoinAddReq.addHeader(HttpHeaders.RANGE, String.format(RANGE_HEADER, checkpoint));
            } else {
                uploadFilecoinAddReq.addHeader(HttpHeaders.RANGE, String.format(RANGE_HEADER, 0));
            }
            uploadFilecoinAddReq.addHeader(HttpHeaders.CONNECTION, HTTP_OBJECT_CONNECTION);
            uploadFilecoinAddReq.setMethod(HttpMethod.POST);
            uploadFilecoinAddReq.setContentLength(uploadFileCoinFileReq.getFileSize());
            uploadFilecoinAddReq.setUrl(uploadFilecoinSignDTO.getData().getNodeAddr());
            ResultResponse<String> resultResponse = commonParserExcute(uploadFilecoinAddReq, String.class);
            if (callBackFinish != null) {
                try {
                    UploadCallbackReq uploadCallbackReq = new UploadCallbackReq();
                    uploadCallbackReq.setUploadid(uploadFilecoinSignDTO.getData().getUploadId());
                    uploadCallbackReq.setFilepath(uploadFilecoinSignDTO.getData().getFilepath());
                    uploadCallbackReq.setCid(resultResponse.getData());
                    uploadCallbackReq.setSuccess(resultResponse.isSuccess());
                    uploadCallbackReq.setMsg(resultResponse.getMsg());
                    callBackFinish.invoke(uploadCallbackReq);
                } catch (Exception e) {
                    logException("Unable to invoke callBackFinish error: ", e.getMessage());
                }
            }
            return resultResponse;
        }
        return ResultResponse.fail(uploadFilecoinSignDTO.getCode(), uploadFilecoinSignDTO.getMsg());
    }

    private Long getCheckpoint(UploadFilecoinCheckpointReq uploadFilecoinCheckpointReq) {
        Request request = new MFSSRequestBuilder<>(uploadFilecoinCheckpointReq, false).build();
        request.setResourcePath(ResourePathConstant.UPLOAD_FILECOIN_CHECKPOINT_RESOURCE);
        ResultResponse<Long> checkpoint = commonParserExcute(request, Long.class);
        if (checkpoint.isSuccess()) {
            return checkpoint.getData();
        }
        return 0l;
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

    public ResultResponse<Void> downloadFileCoinFile(String cid, String uploadid, String saveDir) {
        Map<String, String> param = new HashMap<>();
        param.put("cid", cid);
        param.put("uploadid", uploadid);
        Request request = new MFSSRequestBuilder<>(param).build();
        request.setResourcePath(ResourePathConstant.FILECOIN_DOWNLOAD_SIGN);
        ResultResponse<FilecoindownloadRes> response = commonParserExcute(request, FilecoindownloadRes.class);
        if (!response.isSuccess()) {
            return ResultResponse.fail(response.getMsg());
        }
        FilecoindownloadRes data = response.getData();
        DownloadFileCoinFileReq download = new DownloadFileCoinFileReq(data);
        Request dr = new MFSSRequestBuilder<>(download, false).build();
        dr.setResourcePath(ResourePathConstant.FILECOIN_DOWNLOAD);
        dr.setUrl(data.getNodeAddr());
        ServiceClient.Request httpRequest = buildRequest(dr);
        try {
            downloadFile(httpRequest.getUri(), saveDir, data.getFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultResponse.suc();
    }
}

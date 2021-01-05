package com.mtyw.storage.internal;


import com.mtyw.storage.HttpMethod;
import com.mtyw.storage.common.CallBack;
import com.mtyw.storage.common.MFSSRequestBuilder;
import com.mtyw.storage.common.Request;
import com.mtyw.storage.common.ServiceClient;
import com.mtyw.storage.constant.ResourePathConstant;
import com.mtyw.storage.exception.MtywApiException;
import com.mtyw.storage.model.request.filecoin.UploadFileCoinFileRequest;
import com.mtyw.storage.model.request.filecoin.UploadFilecoinRequest;
import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.model.response.filecoin.UploadFilecoinSignDTO;
import com.mtyw.storage.util.HttpHeaders;

import static com.mtyw.storage.constant.MFSSConstants.DEFAULT_OBJECT_CONTENT_TYPE;
import static com.mtyw.storage.constant.MFSSConstants.HTTP_OBJECT_CONNECTION;

public class FileCoinOperation extends FileCommonOperation {
    public FileCoinOperation(ServiceClient client , String accesskey, String accesssecret) {
        super(client, accesskey,accesssecret);
    }


    public ResultResponse uploadFilecoinFile(UploadFileCoinFileRequest uploadFileCoinFileRequest, CallBack callBack) throws MtywApiException {
        Request request = new MFSSRequestBuilder<>(uploadFileCoinFileRequest).build();
        request.setResourcePath(ResourePathConstant.UPLOADFILECOINSIGN_RESOURCE);
        ResultResponse<UploadFilecoinSignDTO> uploadFilecoinSignDTO = commonParserExcute(request, UploadFilecoinSignDTO.class);
        if (uploadFilecoinSignDTO.isSuccess()) {
            UploadFilecoinRequest uploadFilecoinRequest = new UploadFilecoinRequest();
            uploadFilecoinRequest.setNodeAddr(uploadFilecoinSignDTO.getData().getNodeAddr());
            uploadFilecoinRequest.setNodeIp(uploadFilecoinSignDTO.getData().getNodeIp());
            uploadFilecoinRequest.setSign(uploadFilecoinSignDTO.getData().getSign());
            uploadFilecoinRequest.setTimestamp(uploadFilecoinSignDTO.getData().getTimestamp());
            uploadFilecoinRequest.setDays(uploadFilecoinSignDTO.getData().getDays());
            uploadFilecoinRequest.setFileName(uploadFilecoinSignDTO.getData().getFileName());
            uploadFilecoinRequest.setFileSize(uploadFilecoinSignDTO.getData().getFileSize());
            uploadFilecoinRequest.setUploadid(uploadFilecoinSignDTO.getData().getUploadId());
            uploadFilecoinRequest.setUserId(uploadFilecoinSignDTO.getData().getUserId());
            Request uploadFilecoinSignRequest = new MFSSRequestBuilder<>(uploadFileCoinFileRequest).build();
            uploadFilecoinSignRequest.setResourcePath(ResourePathConstant.UPLOADFILECOIN_RESOURCE);
            uploadFilecoinSignRequest.addHeader(HttpHeaders.CONTENT_TYPE, DEFAULT_OBJECT_CONTENT_TYPE);
            uploadFilecoinSignRequest.addHeader(HttpHeaders.CONNECTION, HTTP_OBJECT_CONNECTION);

            uploadFilecoinSignRequest.setMethod(HttpMethod.POST);
            uploadFilecoinSignRequest.setContentLength(uploadFileCoinFileRequest.getFileSize());
            uploadFilecoinSignRequest.setContent(uploadFileCoinFileRequest.getInputStream());
            uploadFilecoinSignRequest.setUrl(uploadFilecoinSignDTO.getData().getNodeAddr());
            ResultResponse<String> resultResponse = commonParserExcute(uploadFilecoinSignRequest, String.class);
            if (callBack != null) {
                try{
                    callBack.invoke(callBack.getArgs());
                }catch (Exception e) {

                }
            }
        }
        return ResultResponse.suc();
    }
}

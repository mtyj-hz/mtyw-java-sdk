package com.mtyw.storage.internal;


import com.mtyw.storage.common.*;
import com.mtyw.storage.constant.ResourePathConstant;
import com.mtyw.storage.model.request.ipfs.CreateDirRequest;
import com.mtyw.storage.model.response.ResultResponse;
public class IpfsFileOperation extends FileCommonOperation {
    public IpfsFileOperation(ServiceClient client , String accesskey, String accesssecret) {
        super(client, accesskey,accesssecret);
    }

    public ResultResponse createDir(CreateDirRequest createDirRequest) {
        Request request = new MFSSRequestBuilder<>(createDirRequest).build();
        request.setResourcePath(ResourePathConstant.CREATEDIR_RESOURCE);
        request.addHeader("token","eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5MCIsImlhdCI6MTYwNzQyNTIyMn0.HGmoPPVMPfewPTdGFN4J3MnSAJBOx6GPb3eoV8fpdIE");
        ResultResponse resultResponse = commonParserExcute(request, ResultResponse.class);
        return resultResponse;
    }

}
